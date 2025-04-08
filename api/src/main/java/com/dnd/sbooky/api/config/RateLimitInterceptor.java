package com.dnd.sbooky.api.config;

import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    // fixme: 특정 클라이언트의 API를 과도하게 호출하는 경우에 대비할 순 없을까?
    private static final String GLOBAL_API_KEY = "GLOBAL_API_KEY";

    private final RateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        ConsumptionProbe probe = rateLimiter.checkRateLimit(GLOBAL_API_KEY);

        if (probe.isConsumed()) {
            handleAllowedRequest(response, probe);
            return true;
        }

        handleRateLimitedRequest(response, probe);
        return false;
    }

    private void handleAllowedRequest(HttpServletResponse response, ConsumptionProbe probe) {
        long remainingTokens = probe.getRemainingTokens();
        response.addHeader("X-Rate-Limit-Remaining", Long.toString(remainingTokens));
        log.info("Success to consume 1 token! Remaining tokens: {}", remainingTokens);
    }

    private void handleRateLimitedRequest(HttpServletResponse response, ConsumptionProbe probe)
            throws IOException {

        long waitTime = probe.getNanosToWaitForRefill() / 1_000_000_000;
        response.setStatus(429);
        response.addHeader("X-Rate-Limit-Retry-After-Seconds", Long.toString(waitTime));
        response.getWriter().write("Rate limit exceeded. Try again in " + waitTime + " seconds.");
        log.warn("Rate limit exceeded. Remaining tokens: {}", probe.getRemainingTokens());
    }
}

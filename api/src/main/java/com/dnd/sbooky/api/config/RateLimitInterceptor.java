package com.dnd.sbooky.api.config;

import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final String CLIENT_KEY_PREFIX = "rate-limit:";

    private final RateLimiter rateLimiter;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            handleUnauthorized(response);
            return false;
        }

        String clientKey = CLIENT_KEY_PREFIX + authentication.getName();
        ConsumptionProbe probe = rateLimiter.checkRateLimit(clientKey);

        if (probe.isConsumed()) {
            handleAllowedRequest(response, probe);
            return true;
        }

        handleRateLimitedRequest(response, probe);
        return false;
    }

    private void handleUnauthorized(HttpServletResponse response) throws IOException {

        response.setStatus(ErrorType.AUTHENTICATION_FAILED.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getWriter(), ApiResponse.error(ErrorType.AUTHENTICATION_FAILED));
    }

    private void handleAllowedRequest(HttpServletResponse response, ConsumptionProbe probe) {
        long remainingTokens = probe.getRemainingTokens();
        response.addHeader("X-Rate-Limit-Remaining", Long.toString(remainingTokens));
    }

    private void handleRateLimitedRequest(HttpServletResponse response, ConsumptionProbe probe)
            throws IOException {

        long retryAfterSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
        Map<String, Long> errorData = new HashMap<>();
        errorData.put("retryAfter", retryAfterSeconds);

        response.setStatus(ErrorType.RATE_LIMIT_EXCEEDED.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(
                response.getWriter(), ApiResponse.error(ErrorType.RATE_LIMIT_EXCEEDED, errorData));
    }
}

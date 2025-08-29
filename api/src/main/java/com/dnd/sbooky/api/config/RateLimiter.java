package com.dnd.sbooky.api.config;

import com.dnd.sbooky.api.support.circuitbreaker.CircuitBreakerProvider;
import com.dnd.sbooky.core.redis.RedisRepository;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimiter {

    private final ProxyManager<String> proxyManager;
    private final RateLimitPolicy rateLimitPolicy;
    private final RedisRepository redisRepository;

    @CircuitBreaker(name = CircuitBreakerProvider.RATE_LIMITER, fallbackMethod = "allowRequest")
    public ConsumptionProbe checkRateLimit(String clientKey) {
        Bucket bucket = proxyManager.builder().build(clientKey, rateLimitPolicy::createBucketConfig);
        return bucket.tryConsumeAndReturnRemaining(1);
    }

    public ConsumptionProbe allowRequest(String clientKey, Throwable e) {
        log.warn(
                "Circuit breaker triggered for RateLimiter, allowing request. Error = {}", e.getMessage());
        return ConsumptionProbe.consumed(1, Long.MAX_VALUE);
    }
}

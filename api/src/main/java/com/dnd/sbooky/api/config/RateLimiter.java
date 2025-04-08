package com.dnd.sbooky.api.config;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimiter {

    private final ProxyManager<String> proxyManager;
    private final RateLimitPolicy rateLimitPolicy;

    public ConsumptionProbe checkRateLimit(String clientKey) {

        Bucket bucket = proxyManager.builder().build(clientKey, rateLimitPolicy::createBucketConfig);
        return bucket.tryConsumeAndReturnRemaining(1);
    }
}

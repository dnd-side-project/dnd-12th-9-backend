package com.dnd.sbooky.api.config;

import io.github.bucket4j.BucketConfiguration;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimitPolicy {

    // spotless:off
    private static final int INTERVAL_CAPACITY = 625;
    private static final int INTERVAL_REFILL = 625;
    private static final int INTERVAL_DURATION_MINUTES = 30;

    /**
     * 30분 제한 - 일일 제한을 48개 구간으로 나눔 (30,000 / 48 = 625)
     */
    public BucketConfiguration createBucketConfig() {
        return BucketConfiguration
                .builder()
                .addLimit(limit -> limit
                        .capacity(INTERVAL_CAPACITY)
                        .refillIntervally(INTERVAL_REFILL, Duration.ofMinutes(INTERVAL_DURATION_MINUTES)))
                .build();
    }

    // spotless:on
}

package com.dnd.sbooky.api.config;

import io.github.bucket4j.BucketConfiguration;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimitPolicy {

    // spotless:off

    // todo: 해당 값을 테스트를 통해 조정할 계획입니다.
    private static final int INTERVAL_CAPACITY = 30;
    private static final int INTERVAL_REFILL = 5;
    private static final int INTERVAL_DURATION_SECONDS = 20;

    /**
     * 기본 30개의 토큰을 20초마다 5개씩 그리디하게 리필하는 RateLimit 정책을 설정합니다.
     */
    public BucketConfiguration createBucketConfig() {
        return BucketConfiguration
                .builder()
                .addLimit(limit -> limit
                        .capacity(INTERVAL_CAPACITY)
                        .refillGreedy(INTERVAL_REFILL, Duration.ofSeconds(INTERVAL_DURATION_SECONDS)))
                .build();
    }
    // spotless:on
}

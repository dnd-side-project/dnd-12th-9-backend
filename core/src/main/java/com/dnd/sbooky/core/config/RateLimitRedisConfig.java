package com.dnd.sbooky.core.config;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RateLimitRedisConfig {

    // todo: 해당 값은 테스트를 통해 조정할 계획입니다.
    private static final int EXPIRE_MINUTES = 1;
    private static final Long REDIS_TIMEOUT_MILLIS = 1_500L;

    private final RedisProperties redisProperties;

    @Bean
    public RedisClient redisClient() {
        RedisURI.Builder builder =
                RedisURI.builder().withSentinelMasterId(redisProperties.getSentinel().getMaster());

        redisProperties
                .getSentinel()
                .getNodes()
                .forEach(
                        node ->
                                builder
                                        .withSentinel(node.getHost(), node.getPort())
                                        .withPassword(redisProperties.getPassword().toCharArray()));

        RedisURI redisURI = builder.build();
        return RedisClient.create(redisURI);
    }

    /**
     * Redis를 사용하여 분산 환경에서 동작하는 Bucket4j의 ProxyManager를 생성합니다.
     *
     * @return Redis 기반의 ProxyManager 인스턴스
     * <p>
     * 이 메서드는 다음과 같은 작업을 수행합니다:
     * <ol>
     *     <li>Redis 클라이언트를 사용하여 Redis 연결을 생성합니다.</li>
     *     <li>연결에 UTF-8 문자열 키와 바이트 배열 값을 사용하는 RedisCodec을 적용합니다.</li>
     *     <li>Bucket4jLettuce를 사용하여 CAS(Compare-And-Swap) 기반의 ProxyManager 빌더를 생성합니다.</li>
     *     <li>만료 전략을 설정합니다. 버킷이 최대 용량까지 리필되는 데 필요한 시간에 기반하여 만료되도록 합니다.</li>
     *     <li>설정된 옵션으로 ProxyManager를 빌드하여 반환합니다.</li>
     * </ol>
     */
    @Bean
    public ProxyManager<String> lettuceBasedProxyManager(RedisClient redisClient) {

        StatefulRedisConnection<String, byte[]> redisConnection =
                redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

        redisConnection.setTimeout(Duration.ofMillis(REDIS_TIMEOUT_MILLIS));

        return Bucket4jLettuce.casBasedBuilder(redisConnection)
                .expirationAfterWrite(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(
                                Duration.ofMinutes(EXPIRE_MINUTES)))
                .build();
    }
}

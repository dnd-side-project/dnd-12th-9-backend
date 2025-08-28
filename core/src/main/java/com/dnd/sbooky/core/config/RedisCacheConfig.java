package com.dnd.sbooky.core.config;

import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis Cache 설정 클래스
 *
 * @author Seungjo, Jeong
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

    // todo: 해당 값은 테스트를 통해 조정할 계획입니다.
    private static final Long CACHE_TTL_MINUTES = 30L;

    @Bean
    public CacheManager redisCacheManager(LettuceConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(CACHE_TTL_MINUTES))
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new StringRedisSerializer()))
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new GenericJackson2JsonRedisSerializer()))
                        .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfig).build();
    }
}

package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration class.
 *
 * @author Seungjo, Jeong
 */
@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    private static final int COUNT_CACHE_EXPIRATION_MINUTES = 60;
    private static final int DATA_CACHE_EXPIRATION_MINUTES = 60;

    /**
     * 1차 캐시: 쿼리 카운트 캐시
     */
    @Bean(name = "countCache")
    public Cache<String, Integer> queryCountCache() {
        return Caffeine.newBuilder()
                .recordStats()
                .initialCapacity(10)
                .maximumSize(100)
                .expireAfterWrite(COUNT_CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 2차 캐시: 실제 데이터 캐시 (카카오 API 응답 캐시)
     */
    @Bean(name = "dataCache")
    public Cache<String, SearchBookResponse> caffeineConfig() {
        return Caffeine.newBuilder()
                .recordStats()
                .initialCapacity(10)
                .maximumSize(30)
                .evictionListener(
                        (key, value, cause) ->
                                log.info("[Cache eviction] key {} was evicted ({}): {}", key, cause, value))
                .expireAfterWrite(DATA_CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build();
    }
}

package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Cache logging class.
 *
 * @author Seungjo, Jeong
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheLogging {

    private final Cache<String, SearchBookResponse> dataCache;

    /**
     * 2차 캐시 통계를 로그로 출력합니다.
     */
    public void logCacheStats() {

        CacheStats stats = dataCache.stats();

        long requestCount = stats.requestCount();
        double hitRate = stats.hitRate() * 100;
        double missRate = stats.missRate() * 100;
        double avgLoadPenaltyMs = stats.averageLoadPenalty() / 1_000_000.0; // ns → ms
        double loadFailureRate = stats.loadFailureRate() * 100;
        double evictionRate =
                requestCount == 0 ? 0.0 : (double) stats.evictionCount() / requestCount * 100;

        log.info(
                "[Cache: {}] Hit: {}, Miss: {}, Request: {}, HitRate: {}%, MissRate: {}%, Eviction: {},"
                        + " EvictionRate: {}%, LoadSuccess: {}, LoadFailure: {}, LoadFailureRate: {}%,"
                        + " TotalLoadTime(ms): {}, AvgLoadPenalty(ms): {}",
                "DataCache",
                stats.hitCount(),
                stats.missCount(),
                requestCount,
                String.format("%.2f", hitRate),
                String.format("%.2f", missRate),
                stats.evictionCount(),
                String.format("%.2f", evictionRate),
                stats.loadSuccessCount(),
                stats.loadFailureCount(),
                String.format("%.2f", loadFailureRate),
                stats.totalLoadTime() / 1_000_000,
                String.format("%.2f", avgLoadPenaltyMs));
    }
}

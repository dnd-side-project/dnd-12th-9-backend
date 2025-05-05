package com.dnd.sbooky.api.support.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Cache scheduler class.
 *
 * @author Seungjo, Jeong
 */
@Component
@EnableScheduling
@RequiredArgsConstructor
public class CacheScheduler {

    private final CacheLogging cacheLogging;

    /**
     * 1시간 마다 캐시 통계를 로그로 출력합니다.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void logCacheStats() {
        cacheLogging.logCacheStats();
    }
}

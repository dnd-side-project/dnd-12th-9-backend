package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * CaffeineCacheManager class.
 *
 * @author Seungjo, Jeong
 */
@Component
@RequiredArgsConstructor
public class CaffeineCacheManager implements CustomCacheManager<SearchBookResponse> {

    private final Cache<String, Integer> countCache;
    private final Cache<String, SearchBookResponse> dataCache;

    private static final int THRESHOLD = 10;

    @Override
    public void addToCache(String key, SearchBookResponse value) {

        // todo: 동시성 문제는 없을까?
        int count = countCache.get(key, k -> 0) + 1;
        countCache.put(key, count);

        if (count >= THRESHOLD) {
            dataCache.put(key, value);
        }
    }

    @Override
    public void clear() {
        countCache.invalidateAll();
        dataCache.invalidateAll();
    }

    @Override
    public SearchBookResponse getFromCache(String key) {

        // 1차 캐시의 카운트가 THRESHOLD 미만인 경우 null을 반환합니다.
        if (countCache.get(key, k -> 0) < THRESHOLD) {
            return null;
        }

        return dataCache.getIfPresent(key);
    }
}

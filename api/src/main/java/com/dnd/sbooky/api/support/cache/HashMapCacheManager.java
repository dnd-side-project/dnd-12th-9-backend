package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * HashMap Cache Manager.
 *
 * @author Seungjo, Jeong
 */
@Slf4j
@Component
public class HashMapCacheManager implements CustomCacheManager<SearchBookResponse> {

    private static final int MAX_CACHE_SIZE = 10;

    private final Map<String, SearchBookResponse> cache = new HashMap<>();

    @Override
    public void addToCache(String key, SearchBookResponse value) {
        if (cache.size() >= MAX_CACHE_SIZE) {

            // 캐시 저장 공간이 부족하므로 랜덤 키를 제거
            log.info("CacheManager - Cache size exceeded. Removing a random entry.");
            String randomKey = cache.keySet().iterator().next();
            cache.remove(randomKey);
            log.info("CacheManager - Removed key: {}", randomKey);
        }

        log.info("CacheManager - addToCache() : key = {}, value = {}", key, value);
        cache.put(key, value);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public SearchBookResponse getFromCache(String key) {
        return cache.get(key);
    }
}

package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * LinkedHashMap Cache Manager.
 *
 * @author Seungjo, Jeong
 */
@Slf4j
@Component
public class LinkedHashMapCacheManager implements CustomCacheManager<SearchBookResponse> {

    private static final int MAX_CACHE_SIZE = 10;


    private final Map<String, SearchBookResponse> cache = new LinkedHashMap<>(
            MAX_CACHE_SIZE,
            0.75f,
            true
    ) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, SearchBookResponse> eldest) {
            boolean shouldRemove = size() > MAX_CACHE_SIZE;
            if (shouldRemove) {
                log.info("CacheManager - Cache size exceeded. Removing eldest entry: key={}, value={}",
                        eldest.getKey(), eldest.getValue());
            }
            return shouldRemove;
        }
    };

    @Override
    public void addToCache(String key, SearchBookResponse value) {
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

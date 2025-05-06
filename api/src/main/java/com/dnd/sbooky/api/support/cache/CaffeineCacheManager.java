package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.book.v1.response.SearchBookResponse;
import com.github.benmanes.caffeine.cache.Cache;
import java.util.Optional;
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
    public Integer incrementCount(String key) {
        return countCache.asMap().compute(key, (k, v) -> v == null ? 1 : v + 1);
    }

    @Override
    public void addToCache(String key, SearchBookResponse value) {

        Integer count = incrementCount(key);

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
    public Optional<SearchBookResponse> getFromCache(String key) {

        Integer count = countCache.getIfPresent(key);
        if (count == null || count < THRESHOLD) {
            return Optional.empty();
        }

        return Optional.ofNullable(dataCache.getIfPresent(key));
    }
}

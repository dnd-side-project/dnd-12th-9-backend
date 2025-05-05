package com.dnd.sbooky.api.support.cache;

import java.util.Optional;

/**
 * Cache Manager interface for SearchBook.
 *
 * @author Seungjo, Jeong
 */
public interface CustomCacheManager<T> {

    void addToCache(String key, T value);

    void clear();

    Optional<T> getFromCache(String key);
}

package com.dnd.sbooky.api.support.cache;

/**
 * Cache Manager interface for SearchBook.
 *
 * @author Seungjo, Jeong
 */
public interface CustomCacheManager<T> {

    void addToCache(String key, T value);

    void clear();

    T getFromCache(String key);
}

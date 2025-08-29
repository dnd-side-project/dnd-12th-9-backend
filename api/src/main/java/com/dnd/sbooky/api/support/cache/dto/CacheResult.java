package com.dnd.sbooky.api.support.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CacheResult<T> {

    private final T data;
    private final Long delta;
    private final Long remainingTtl;
    private final boolean cacheHit;

    public static CacheResult<String> miss() {
        return new CacheResult<>(null, null, null, false);
    }
}

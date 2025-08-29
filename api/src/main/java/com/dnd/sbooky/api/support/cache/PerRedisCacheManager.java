package com.dnd.sbooky.api.support.cache;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.cache.dto.CacheResult;
import com.dnd.sbooky.api.support.cache.exception.CacheException;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.redis.LuaScriptRepository;
import com.dnd.sbooky.core.redis.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerRedisCacheManager {

    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;
    private final LuaScriptRepository luaScriptRepository;

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final long DEFAULT_TTL_MS = 60_000; // 데이터 TTL
    private static final long DEFAULT_LOCK_TTL_MS = 3_000; // 분산락 TTL
    private static final int RETRY_ATTEMPTS = 3; // follower 재시도 횟수
    private static final long BASE_BACKOFF_MS = 50; // follower 기본 backoff
    private static final long MAX_JITTER_MS = 50; // follower 지터
    private static final double BETA = 1.0; // PER 계수

    public <T> T getOrLoad(String key, Class<T> valueType, Supplier<T> recompute) {
        try {
            CacheResult<String> cacheResult = getCacheData(key);

            // 1. Cache miss: Single Flight Pattern
            if (!cacheResult.isCacheHit() || cacheResult.getData() == null) {
                T newData = tryRecomputeSingleFlight(key, recompute);

                if (newData != null) {
                    return newData;
                }

                return retryGetFromCache(key, valueType);
            }

            // 2. Cache hit: PER check
            if (shouldRecompute(cacheResult)) {
                T newData = tryRecomputeSingleFlight(key, recompute);
                if (newData != null) {
                    return newData;
                }
            }

            return deserializeData(cacheResult.getData(), valueType);
        } catch (Exception e) {
            throw new CacheException(ErrorType.CACHE_ERROR);
        }
    }

    private <T> T retryGetFromCache(String key, Class<T> valueType) {
        for (int i = 0; i < RETRY_ATTEMPTS; i++) {
            sleep(BASE_BACKOFF_MS + RANDOM.nextLong(MAX_JITTER_MS));
            CacheResult<String> retryResult = getCacheData(key);
            if (retryResult.isCacheHit() && retryResult.getData() != null) {
                return deserializeData(retryResult.getData(), valueType);
            }
        }

        throw new CacheException(ErrorType.CACHE_ERROR);
    }

    private <T> T tryRecomputeSingleFlight(String key, Supplier<T> recompute) {
        String token = acquireLock(key, Duration.ofMillis(DEFAULT_LOCK_TTL_MS));

        if (token == null) {
            return null;
        }

        try {
            long start = System.currentTimeMillis();
            T newData = recompute.get();
            long computeTime = System.currentTimeMillis() - start;

            put(key, newData, computeTime);
            return newData;
        } finally {
            releaseLock(key, token);
        }
    }

    private String acquireLock(String key, Duration ttl) {
        String lockKey = RedisKey.getLockKey(key);
        String token = UUID.randomUUID().toString();

        Boolean isOk = redisRepository.setIfAbsent(lockKey, token, ttl);
        return Boolean.TRUE.equals(isOk) ? token : null;
    }

    private void releaseLock(String key, String token) {
        luaScriptRepository.executeUnlock(List.of(RedisKey.getLockKey(key)), token);
    }

    @SuppressWarnings("unchecked")
    private CacheResult<String> getCacheData(String key) {
        List<Object> result =
                luaScriptRepository.executeCacheGet(List.of(key, RedisKey.getDeltaKey(key)));

        if (result.size() < 2) {
            return CacheResult.miss();
        }

        Object firstResult = result.get(0);
        if (!(firstResult instanceof List)) {
            return CacheResult.miss();
        }

        List<Object> valueList = (List<Object>) firstResult;
        if (valueList.size() < 2) {
            return CacheResult.miss();
        }

        String cachedData = (String) valueList.get(0);
        Integer delta = (Integer) valueList.get(1);
        Long remainingTtl = (Long) result.get(1);
        boolean hit = cachedData != null;

        return new CacheResult<>(cachedData, delta, remainingTtl, hit);
    }

    private boolean shouldRecompute(CacheResult<String> cacheResult) {
        if (!cacheResult.isCacheHit()
                || cacheResult.getData() == null
                || cacheResult.getDelta() == null
                || cacheResult.getRemainingTtl() == null) {
            return true;
        }

        // PER 계산
        double randomValue = RANDOM.nextDouble();
        double logRandom = Math.log(randomValue);
        double threshold = cacheResult.getDelta() * BETA * (-logRandom);
        long remainingTtl = cacheResult.getRemainingTtl();
        return remainingTtl <= threshold;
    }

    private <T> void put(String key, T value, long computationTime) {
        try {
            String deltaKey = RedisKey.getDeltaKey(key);
            String serializedValue = serializeValue(value);

            luaScriptRepository.executeCacheSet(
                    List.of(key, deltaKey), serializedValue, computationTime, DEFAULT_TTL_MS);
        } catch (Exception e) {
            throw new CacheException(ErrorType.CACHE_ERROR);
        }
    }

    private <T> T deserializeData(String cachedData, Class<T> valueType) {
        try {
            return objectMapper.readValue(cachedData, valueType);
        } catch (JsonProcessingException e) {
            throw new CacheException(ErrorType.CACHE_ERROR);
        }
    }

    private <T> String serializeValue(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new CacheException(ErrorType.CACHE_ERROR);
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

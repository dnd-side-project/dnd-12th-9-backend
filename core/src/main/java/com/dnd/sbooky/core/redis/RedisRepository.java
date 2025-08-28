package com.dnd.sbooky.core.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value, Duration expiration) {
        redisTemplate.opsForValue().set(key, value, expiration);
    }

    public <T> T getData(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return clazz.cast(value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void expire(String key, Duration expiration) {
        redisTemplate.expire(key, expiration);
    }

    public Boolean setIfAbsent(String key, String value, Duration expiration) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expiration);
    }
}

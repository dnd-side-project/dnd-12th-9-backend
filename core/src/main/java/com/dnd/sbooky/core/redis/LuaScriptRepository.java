package com.dnd.sbooky.core.redis;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LuaScriptRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisScript<List> cacheGetRedisScript;
    private final RedisScript<String> cacheSetRedisScript;
    private final RedisScript<Long> unlockScript;

    public List executeCacheGet(List<String> keys) {
        return redisTemplate.execute(cacheGetRedisScript, keys);
    }

    public String executeCacheSet(List<String> keys, Object... args) {
        return redisTemplate.execute(cacheSetRedisScript, keys, args);
    }

    public Long executeUnlock(List<String> keys, Object... args) {
        return redisTemplate.execute(unlockScript, keys, args);
    }
}

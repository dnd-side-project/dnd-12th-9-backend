package com.dnd.sbooky.api.support;

public class RedisKey {

    private RedisKey() {
        // Prevent instantiation
        throw new UnsupportedOperationException();
    }

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";
    private static final String LOCK_PREFIX = "lock:";
    private static final String DELTA_PREFIX = "delta:";
    private static final String RATE_LIMIT_PREFIX = "rateLimit:";
    private static final String BOOK_CACHE_PREFIX = "book:";

    public static String getRefreshTokenKey(String memberId) {
        return REFRESH_TOKEN_PREFIX + memberId;
    }

    public static String getLockKey(String key) {
        return LOCK_PREFIX + key;
    }

    public static String getDeltaKey(String key) {
        return DELTA_PREFIX + key;
    }

    public static String getRateLimitKey(String key) {
        return RATE_LIMIT_PREFIX + key;
    }

    public static String getBookCacheKey(String query, int page) {
        return BOOK_CACHE_PREFIX + query + ":" + page;
    }
}

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
        StringBuilder sb = new StringBuilder();
        return sb.append(REFRESH_TOKEN_PREFIX).append(memberId).toString();
    }

    public static String getLockKey(String key) {
        StringBuilder sb = new StringBuilder();
        return sb.append(LOCK_PREFIX).append(key).toString();
    }

    public static String getDeltaKey(String key) {
        StringBuilder sb = new StringBuilder();
        return sb.append(DELTA_PREFIX).append(key).toString();
    }

    public static String getRateLimitKey(String key) {
        StringBuilder sb = new StringBuilder();
        return sb.append(RATE_LIMIT_PREFIX).append(key).toString();
    }

    public static String getBookCacheKey(String query, int page) {
        StringBuilder sb = new StringBuilder();
        return sb.append(BOOK_CACHE_PREFIX).append(query).append(":").append(page).toString();
    }
}

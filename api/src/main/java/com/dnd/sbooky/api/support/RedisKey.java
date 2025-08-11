package com.dnd.sbooky.api.support;

public class RedisKey {

    private RedisKey() {
        // Prevent instantiation
        throw new UnsupportedOperationException();
    }

    public static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public static String getRefreshTokenKey(String memberId) {
        StringBuilder sb = new StringBuilder();
        return sb.append(REFRESH_TOKEN_PREFIX).append(memberId).toString();
    }
}

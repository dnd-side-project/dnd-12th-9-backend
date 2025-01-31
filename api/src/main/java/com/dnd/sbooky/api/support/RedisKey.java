package com.dnd.sbooky.api.support;

public class RedisKey {
    public static final String refreshTokenPrefix = "refreshToken:";

    public static String getRefreshTokenKey(String memberId) {
        StringBuilder sb = new StringBuilder();
        return sb.append(refreshTokenPrefix).append(memberId).toString();
    }
}

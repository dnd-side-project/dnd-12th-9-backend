package com.dnd.sbooky.api.security;

public class TokenConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN = "refreshToken";

    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L; // 30 minutes

    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14L; // 14 days

    public static final String KEY_ROLE = "role";
}

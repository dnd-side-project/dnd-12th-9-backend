package com.dnd.sbooky.api.security;

public class TokenConstants {

    private TokenConstants() {
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN = "refreshToken";

    // fixme : ACCESS TOKEN EXPIRE TIME 변경해야함!!
    //    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L; // 30 minutes
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L; // 임시로 7일 설정

    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14L; // 14 days

    public static final String KEY_ROLE = "role";

    public static final String QUERY_PARAM = "environment";

}

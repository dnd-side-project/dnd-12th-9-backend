package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.TokenConstants.*;
import static java.nio.charset.StandardCharsets.*;
import static org.springframework.http.HttpHeaders.*;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.api.support.response.ResultType;
import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.core.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{

        String accessToken = tokenProvider.generateAccessToken(authentication);
        setAccessTokenHeader(response, accessToken);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        setRefreshTokenCookie(response, refreshToken);
        redisRepository.setData(getKey(authentication), refreshToken, REFRESH_TOKEN_EXPIRE_TIME);
        objectMapper.writeValue(response.getWriter(),
                new ApiResponse(ResultType.SUCCESS, null, null));
    }

    private String getKey(Authentication authentication) {
        StringBuilder sb = new StringBuilder();
        return sb.append(RedisKey.refreshTokenPrefix).append(authentication.getName()).toString();
    }

    private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(TOKEN_PREFIX).append(accessToken);
        response.setHeader(AUTHORIZATION, sb.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.isCommitted();
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie =
                ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                        .secure(true)
                        .sameSite(SameSite.NONE.getValue())
                        .httpOnly(true)
                        .maxAge(REFRESH_TOKEN_EXPIRE_TIME)
                        .path("/")
                        .build();
        response.addHeader(SET_COOKIE, cookie.toString());
    }
}

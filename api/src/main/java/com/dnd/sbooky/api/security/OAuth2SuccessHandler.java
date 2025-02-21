package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.TokenConstants.*;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.core.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Value("${login.redirect-uri.success}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        redisRepository.setData(getKey(authentication), refreshToken, REFRESH_TOKEN_EXPIRE_TIME);
        String redirectUri =
                UriComponentsBuilder.fromUriString(this.redirectUri)
                        .queryParam("refreshToken", refreshToken)
                        .build()
                        .toUriString();
        response.sendRedirect(redirectUri);
    }

    private String getKey(Authentication authentication) {
        StringBuilder sb = new StringBuilder();
        return sb.append(RedisKey.refreshTokenPrefix).append(authentication.getName()).toString();
    }

    //    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    //        ResponseCookie cookie =
    //                ResponseCookie.from(REFRESH_TOKEN, refreshToken)
    //                        .secure(true)
    //                        .sameSite(SameSite.NONE.getValue())
    //                        .httpOnly(true)
    //                        .maxAge(REFRESH_TOKEN_EXPIRE_TIME)
    //                        .path("/")
    //                        .build();
    //        response.addHeader(SET_COOKIE, cookie.toString());
    //    }

}

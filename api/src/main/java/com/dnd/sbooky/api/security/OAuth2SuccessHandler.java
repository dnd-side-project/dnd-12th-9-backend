package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.TokenConstants.REFRESH_TOKEN_EXPIRE_TIME;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.core.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final LoginRedirectUrlResolver redirectUrlResolver;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        String redisKey = getRedisKey(authentication);
        redisRepository.setData(redisKey, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);

        String callbackUrl = redirectUrlResolver.resolveRedirectUrl(request, false);
        String redirectUrl = buildRedirectUrl(callbackUrl, refreshToken);
        response.sendRedirect(redirectUrl);
    }

    private String getRedisKey(Authentication authentication) {
        return RedisKey.refreshTokenPrefix + authentication.getName();
    }

    private String buildRedirectUrl(String baseUrl, String refreshToken) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();
    }
}

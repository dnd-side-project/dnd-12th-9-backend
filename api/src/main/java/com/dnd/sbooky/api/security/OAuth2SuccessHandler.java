package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.EnvironmentConstants.CLIENT_ENVIRONMENT;
import static com.dnd.sbooky.api.security.TokenConstants.REFRESH_TOKEN_EXPIRE_TIME;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.core.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        redisRepository.setData(getKey(authentication), refreshToken, REFRESH_TOKEN_EXPIRE_TIME);

        String callbackUrl = getCallbackUrl(request) + "/auth/callback";

        String redirectUrl =
                UriComponentsBuilder.fromUriString(callbackUrl)
                        .queryParam("refreshToken", refreshToken)
                        .build()
                        .toUriString();

        response.sendRedirect(redirectUrl);
    }

    private String getKey(Authentication authentication) {
        StringBuilder sb = new StringBuilder();
        return sb.append(RedisKey.refreshTokenPrefix).append(authentication.getName()).toString();
    }

    private String getCallbackUrl(HttpServletRequest request) {

        String callBackUrl = (String) request.getSession().getAttribute(CLIENT_ENVIRONMENT);
        request.getSession().removeAttribute(CLIENT_ENVIRONMENT);

        return callBackUrl;
    }
}

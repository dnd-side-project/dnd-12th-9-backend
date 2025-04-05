package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.EnvironmentConstants.CALLBACK_PATH;
import static com.dnd.sbooky.api.security.EnvironmentConstants.STATE_DELIMITER;
import static com.dnd.sbooky.api.security.TokenConstants.REFRESH_TOKEN_EXPIRE_TIME;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.core.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
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

    private static final String STATE_PARAM = "state";

    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;
    private final RedirectProperties redirectProperties;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        String redisKey = getRedisKey(authentication);
        redisRepository.setData(redisKey, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);

        String baseUrl = resolveRedirectUrl(request);
        String callbackUrl = baseUrl + CALLBACK_PATH;
        String redirectUrl = buildRedirectUrl(callbackUrl, refreshToken);

        log.debug("Redirect URL: {}", redirectUrl);

        response.sendRedirect(redirectUrl);
    }

    private String getRedisKey(Authentication authentication) {
        return RedisKey.refreshTokenPrefix + authentication.getName();
    }

    private String resolveRedirectUrl(HttpServletRequest request) {
        String state = request.getParameter(STATE_PARAM);
        if (state == null || state.isEmpty()) {
            log.error("State parameter is missing");
            throw new IllegalArgumentException("State parameter is missing!");
        }

        try {
            String decodedState = new String(Base64.getDecoder().decode(state));

            if (log.isDebugEnabled()) {
                log.debug("Decoded state: {}", decodedState);
            }

            String[] stateParts = decodedState.split(STATE_DELIMITER);

            if (stateParts.length != 2) {
                log.error("Invalid state format: {}", decodedState);
                throw new IllegalArgumentException("Invalid state parameter format!");
            }

            Environment environment = Environment.fromString(stateParts[0]);
            return getRedirectUrlForEnvironment(environment);

        } catch (IllegalArgumentException e) {
            log.error("Failed to decode state parameter: {}", state, e);
            throw new IllegalArgumentException("Invalid state parameter encoding");
        }
    }

    private String getRedirectUrlForEnvironment(Environment environment) {
        return switch (environment) {
            case LOCAL -> redirectProperties.getLocal();
            case DEV -> redirectProperties.getDev();
            case PROD -> redirectProperties.getProd();
        };
    }

    private String buildRedirectUrl(String baseUrl, String refreshToken) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();
    }
}

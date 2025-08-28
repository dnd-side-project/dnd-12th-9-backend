package com.dnd.sbooky.api.security;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.redis.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler {

    private final ObjectMapper objectMapper;
    private final RedisRepository redisRepository;
    private final TokenProvider tokenProvider;

    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        extractRefreshTokenFromCookies(request)
                .ifPresentOrElse(
                        refreshToken -> processLogout(refreshToken, response),
                        () -> handleMissingToken(response));
    }

    private Optional<String> extractRefreshTokenFromCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private void handleMissingToken(HttpServletResponse response) {

        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(
                    response.getWriter(), ApiResponse.error(ErrorType.AUTHENTICATION_FAILED));
        } catch (IOException e) {
            response.setStatus(SC_BAD_REQUEST);
        }
    }

    private void processLogout(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null
                || refreshToken.isEmpty()
                || !tokenProvider.validateToken(refreshToken)) {
            response.setStatus(SC_BAD_REQUEST);
            return;
        }

        String memberId = tokenProvider.getAuthentication(refreshToken).getName();
        String redisKey = RedisKey.getRefreshTokenKey(memberId);
        redisRepository.delete(redisKey);
    }
}

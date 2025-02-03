package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.TokenConstants.REFRESH_TOKEN;
import static com.dnd.sbooky.api.security.TokenConstants.REFRESH_TOKEN_EXPIRE_TIME;
import static com.dnd.sbooky.api.security.TokenConstants.TOKEN_PREFIX;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.dnd.sbooky.api.docs.spec.AuthApiSpec;
import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController implements AuthApiSpec {

    private final TokenProvider tokenProvider;
    private final TokenUseCase tokenUsecase;

    /**
     * 토큰 재발급시 RTR 방식을 사용하여 RefreshToken이 한번만 사용되도록 한다.
     *
     * @param refreshToken
     * @param response
     * @return
     */
    @PostMapping("/auth/reissue")
    public ApiResponse<?> reissue(
            @CookieValue(value = "refreshToken") String refreshToken,
            HttpServletResponse response) {
        tokenUsecase.validateRefreshToken(refreshToken);
        setToken(response, refreshToken);
        return ApiResponse.success();
    }

    private void setToken(HttpServletResponse response, String refreshToken) {
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        String accessToken = tokenProvider.generateAccessToken(authentication);
        setAccessTokenHeader(response, accessToken);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);
        setRefreshTokenCookie(response, refreshToken);
        tokenUsecase.saveRefreshToken(
                RedisKey.getRefreshTokenKey(authentication.getName()), newRefreshToken);
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

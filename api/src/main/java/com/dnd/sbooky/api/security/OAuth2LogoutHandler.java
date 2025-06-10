package com.dnd.sbooky.api.security;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.core.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler {

    private final RedisRepository redisRepository;
    private final TokenProvider tokenProvider;

    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Arrays.stream(request.getCookies())
              .filter(cookie -> "refreshToken".equals(cookie.getName()))
              .findFirst()
              .ifPresentOrElse(
                      cookie -> {
                          String refreshToken = cookie.getValue();

                          log.info("refreshToken 쿠키 값: {}", refreshToken);
                          if (refreshToken == null || refreshToken.isEmpty()) {
                              log.warn("로그아웃 실패: Refresh token이 비어 있습니다.");
                              response.setStatus(SC_BAD_REQUEST);
                              return;
                          }

                          String memberId = tokenProvider.getAuthentication(refreshToken).getName();
                          String redisKey = RedisKey.refreshTokenPrefix + memberId;
                          boolean deleted = redisRepository.delete(redisKey);
                          if (deleted) {
                              log.info(
                                      "로그아웃 성공: Redis에서 Refresh token 삭제 완료 (사용자: {}).", memberId);
                          } else {
                              log.warn(
                                      "로그아웃 실패: Redis에서 Refresh token을 찾을 수 없습니다 (사용자: {}).", memberId);
                          }
                      },
                      () -> {
                          log.warn("로그아웃 실패: Refresh token 쿠키를 찾을 수 없습니다.");
                          response.setStatus(SC_BAD_REQUEST);
                      });
    }
}

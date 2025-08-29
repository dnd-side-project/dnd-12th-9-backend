package com.dnd.sbooky.api.security;

import static com.dnd.sbooky.api.security.TokenConstants.REFRESH_TOKEN_EXPIRE_TIME;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.redis.RedisRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenUseCase {

    private final RedisRepository redisRepository;
    private final TokenProvider tokenProvider;

    public void saveRefreshToken(String key, String refreshToken) {
        redisRepository.setData(key, refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME));
    }

    public void validateRefreshToken(String refreshToken) {
        if (!(tokenProvider.validateToken(refreshToken) && isMatched(refreshToken))) {
            throw new InvalidTokenException(ErrorType.INVALID_TOKEN);
        }
    }

    private boolean isMatched(String refreshToken) {
        String authToken = tokenProvider.getAuthentication(refreshToken).getName();

        return redisRepository
                .getData(RedisKey.getRefreshTokenKey(authToken), String.class)
                .equals(refreshToken);
    }
}

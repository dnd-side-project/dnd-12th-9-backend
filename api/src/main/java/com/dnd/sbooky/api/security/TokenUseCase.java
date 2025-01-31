package com.dnd.sbooky.api.security;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenUseCase {

    private final RedisRepository redisRepository;
    private final TokenProvider tokenProvider;

    public void saveRefreshToken(String key, String refreshToken) {
        redisRepository.setData(key, refreshToken, TokenConstants.REFRESH_TOKEN_EXPIRE_TIME);
    }

    public void validateRefreshToken(String refreshToken) {
        if (!(tokenProvider.validateToken(refreshToken) && isMatched(refreshToken))) {
            throw new InvalidTokenException(ErrorType.INVALID_TOKEN);
        }
    }

    private boolean isMatched(String refreshToken) {
        return redisRepository
                .getData(
                        RedisKey.getRefreshTokenKey(
                                tokenProvider.getAuthentication(refreshToken).getName()))
                .equals(refreshToken);
    }
}

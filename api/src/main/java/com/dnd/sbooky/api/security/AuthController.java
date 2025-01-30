package com.dnd.sbooky.api.security;

import com.dnd.sbooky.api.support.RedisKey;
import com.dnd.sbooky.api.support.error.ErrorType;
import com.dnd.sbooky.core.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    private void validateRefreshToken(String refreshToken) {
        if(!(tokenProvider.validateToken(refreshToken) && isMatched(refreshToken))){
            throw new InvalidTokenException(ErrorType.INVALID_TOKEN);
        }
    }

    private boolean isMatched(String refreshToken) {
        return redisRepository.getData(
                        RedisKey.getRefreshTokenKey(tokenProvider.getAuthentication(refreshToken).getName()))
                .equals(refreshToken);
    }
}

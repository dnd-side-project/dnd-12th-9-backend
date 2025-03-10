package com.dnd.sbooky.clients.kakao;

import lombok.Getter;

@Getter
public class KakaoApiException extends RuntimeException {

    private final int status;

    public KakaoApiException(String message, int status) {
        super(message);
        this.status = status;
    }
}

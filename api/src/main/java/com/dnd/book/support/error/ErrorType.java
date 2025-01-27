package com.dnd.book.support.error;


import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // TODO: 에러 타입 정의 필요
    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500,
            "An unexpected error has occurred.", LogLevel.ERROR),

    NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404,
            "The requested resource was not found.", LogLevel.INFO),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, ErrorCode.SECURITY_401_1,
            "Invalid token.", LogLevel.INFO),

    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, ErrorCode.SECURITY_401_2,
            "Invalid signature" , LogLevel.INFO),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, ErrorCode.SECURITY_401_3,
            "Expired accessToken" ,LogLevel.INFO);


    private final HttpStatus status;
    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }
}

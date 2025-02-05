package com.dnd.sbooky.api.support.error;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // TODO: 에러 타입 정의

    // Server Error
    DEFAULT_ERROR(INTERNAL_SERVER_ERROR, ErrorCode.E500, "Internal server error.", LogLevel.ERROR),
    INVALID_PARAMETER(BAD_REQUEST, ErrorCode.E400, "Request parameter is invalid.", LogLevel.INFO),

    // Security Error
    INVALID_TOKEN(UNAUTHORIZED, ErrorCode.SECURITY_401_1, "Invalid token.", LogLevel.INFO),
    INVALID_SIGNATURE(UNAUTHORIZED, ErrorCode.SECURITY_401_2, "Invalid signature", LogLevel.INFO),
    EXPIRED_TOKEN(UNAUTHORIZED, ErrorCode.SECURITY_401_3, "Expired accessToken", LogLevel.INFO),
    NOT_FOUND_TOKEN(NOT_FOUND, ErrorCode.SECURITY_404_1, "Not found token", LogLevel.INFO),

    // Member Error
    MEMBER_NOT_FOUND(NOT_FOUND, ErrorCode.MEMBER_404, "Member not found.", LogLevel.INFO),

    // Book Error
    BOOK_ACCESS_FORBIDDEN(FORBIDDEN, ErrorCode.BOOK_403, "Book access is forbidden.", LogLevel.INFO),
    BOOK_NOT_FOUND(NOT_FOUND, ErrorCode.BOOK_404, "Book was not found.", LogLevel.INFO),

    // Item Error
    ITEM_NOT_FOUND(NOT_FOUND, ErrorCode.ITEM_404, "Item was not found.", LogLevel.INFO);

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

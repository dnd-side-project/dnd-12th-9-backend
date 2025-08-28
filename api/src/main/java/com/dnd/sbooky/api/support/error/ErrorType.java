package com.dnd.sbooky.api.support.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    // TODO: 에러 타입 정의
    // spotless:off

    // Server Error
    DEFAULT_ERROR(INTERNAL_SERVER_ERROR, ErrorCode.E500, "Internal server error.", LogLevel.ERROR),
    INVALID_PARAMETER(BAD_REQUEST, ErrorCode.E400_1, "Request parameter is invalid.", LogLevel.INFO),
    REQUEST_VALIDATION_FAILED(BAD_REQUEST, ErrorCode.E400_2, "Request body is invalid", LogLevel.INFO),

    // Security Error
    INVALID_TOKEN(UNAUTHORIZED, ErrorCode.SECURITY_401_1, "Invalid token.", LogLevel.INFO),
    INVALID_SIGNATURE(UNAUTHORIZED, ErrorCode.SECURITY_401_2, "Invalid signature", LogLevel.INFO),
    EXPIRED_TOKEN(UNAUTHORIZED, ErrorCode.SECURITY_401_3, "Expired accessToken", LogLevel.INFO),
    AUTHENTICATION_FAILED(UNAUTHORIZED, ErrorCode.SECURITY_401_4, "Authentication failed.", LogLevel.INFO),
    NOT_FOUND_TOKEN(NOT_FOUND, ErrorCode.SECURITY_404_1, "Not found token", LogLevel.INFO),

    // Member Error
    MEMBER_NOT_FOUND(NOT_FOUND, ErrorCode.MEMBER_404, "Member not found.", LogLevel.INFO),

    // Book Error
    BOOK_ACCESS_FORBIDDEN(FORBIDDEN, ErrorCode.BOOK_403, "Book access is forbidden.", LogLevel.INFO),
    BOOK_NOT_FOUND(NOT_FOUND, ErrorCode.BOOK_404, "Book was not found.", LogLevel.INFO),
    BOOK_READ_STATUS_NOT_COMPLETED(BAD_REQUEST, ErrorCode.BOOK_400_1, "Book read status is not completed.", LogLevel.INFO),

    // Item Error
    ITEM_NOT_FOUND(NOT_FOUND, ErrorCode.ITEM_404, "Item was not found.", LogLevel.INFO),
    MEMBER_HAS_NOT_ITEM(NOT_FOUND, ErrorCode.ITEM_404_2, "Member has not item.", LogLevel.INFO),

    // Evaluation Error
    EVALUATION_KEYWORD_NOT_FOUND(NOT_FOUND, ErrorCode.EVALUATION_404_1, "Evaluation keyword not found.", LogLevel.INFO),

    // Rate Limit Error
    RATE_LIMIT_EXCEEDED(TOO_MANY_REQUESTS, ErrorCode.RATE_LIMIT_429, "Rate limit exceeded. Please try again later.",
            LogLevel.DEBUG),

    // Cache Error
    CACHE_ERROR(INTERNAL_SERVER_ERROR, ErrorCode.CACHE_500, "Cache server error.", LogLevel.ERROR),

    ;

    // spotless:on

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

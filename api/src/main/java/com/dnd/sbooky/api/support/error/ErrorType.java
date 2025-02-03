package com.dnd.sbooky.api.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorType {

    // TODO: 에러 타입 정의 필요
    DEFAULT_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCode.E500,
            "An unexpected error has occurred.",
            LogLevel.ERROR),

    NOT_FOUND(
            HttpStatus.NOT_FOUND,
            ErrorCode.E404,
            "The requested resource was not found.",
            LogLevel.INFO),

    INVALID_TOKEN(
            HttpStatus.UNAUTHORIZED, ErrorCode.SECURITY_401_1, "Invalid token.", LogLevel.INFO),

    INVALID_SIGNATURE(
            HttpStatus.UNAUTHORIZED, ErrorCode.SECURITY_401_2, "Invalid signature", LogLevel.INFO),

    EXPIRED_TOKEN(
            HttpStatus.UNAUTHORIZED,
            ErrorCode.SECURITY_401_3,
            "Expired accessToken",
            LogLevel.INFO),

    NOT_FOUND_TOKEN(
            HttpStatus.NOT_FOUND, ErrorCode.SECURITY_404_1, "Not found token", LogLevel.INFO),

    INVALID_PARAMETER(
            HttpStatus.BAD_REQUEST, ErrorCode.E400, "Request parameter is invalid.", LogLevel.INFO),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.MEMBER_404, "Member not found.", LogLevel.INFO),

    BOOK_ACCESS_FORBIDDEN(HttpStatus.FORBIDDEN, ErrorCode.BOOK_403, "Book access is forbidden.", LogLevel.INFO),

    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.BOOK_404, "Book was not found.", LogLevel.INFO);

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

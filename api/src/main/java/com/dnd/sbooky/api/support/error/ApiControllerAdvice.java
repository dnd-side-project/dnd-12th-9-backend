package com.dnd.sbooky.api.support.error;

import com.dnd.sbooky.api.support.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
        switch (e.getErrorType().getLogLevel()) {
            case ERROR -> log.error("ApiException = {}", e.getMessage(), e);
            case WARN -> log.warn("ApiException = {}", e.getMessage(), e);
            default -> log.info("ApiException = {}", e.getMessage(), e);
        }

        return ResponseEntity
                .status(e.getErrorType().getStatus())
                .body(ApiResponse.error(e.getErrorType(), e.getData()));
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingRequestCookieException(MissingRequestCookieException e) {
        log.error("MissingRequestCookieException = {}", e.getMessage(), e);

        return ResponseEntity
                .status(ErrorType.NOT_FOUND_TOKEN.getStatus())
                .body(ApiResponse.error(ErrorType.NOT_FOUND_TOKEN));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception = {}", e.getMessage(), e);

        return ResponseEntity
                .status(ErrorType.DEFAULT_ERROR.getStatus())
                .body(ApiResponse.error(ErrorType.DEFAULT_ERROR));
    }
}

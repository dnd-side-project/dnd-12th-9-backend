package com.dnd.sbooky.api.support.error;

import com.dnd.sbooky.api.support.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    /**
     * RequestParameter Exception Handler
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleRequestParameterException(MethodArgumentTypeMismatchException e) {

        return ResponseEntity
                .status(ErrorType.INVALID_PARAMETER.getStatus())
                .body(ApiResponse.error(ErrorType.INVALID_PARAMETER));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception = {}", e.getMessage(), e);

        return ResponseEntity
                .status(ErrorType.DEFAULT_ERROR.getStatus())
                .body(ApiResponse.error(ErrorType.DEFAULT_ERROR));
    }
}

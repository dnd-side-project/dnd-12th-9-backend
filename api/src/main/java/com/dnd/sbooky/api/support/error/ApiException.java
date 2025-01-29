package com.dnd.sbooky.api.support.error;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorType errorType;
    private final Object data;

    public ApiException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public ApiException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }
}

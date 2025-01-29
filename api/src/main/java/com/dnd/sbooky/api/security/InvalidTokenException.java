package com.dnd.sbooky.api.security;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException(ErrorType errorType) {
        super(errorType);
    }
}

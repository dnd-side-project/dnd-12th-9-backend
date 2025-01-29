package com.dnd.sbooky.security;

import com.dnd.sbooky.support.error.ApiException;
import com.dnd.sbooky.support.error.ErrorType;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException(ErrorType errorType) {
        super(errorType);
    }
}

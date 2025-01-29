package com.dnd.sbooky.api.security;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class ExpiredTokenException extends ApiException {
    public ExpiredTokenException(ErrorType errorType) {
        super(errorType);
    }
}

package com.dnd.sbooky.security;

import com.dnd.sbooky.support.error.ApiException;
import com.dnd.sbooky.support.error.ErrorType;

public class ExpiredTokenException extends ApiException {
    public ExpiredTokenException(ErrorType errorType) {
        super(errorType);
    }
}

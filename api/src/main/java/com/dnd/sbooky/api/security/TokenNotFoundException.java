package com.dnd.sbooky.api.security;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class TokenNotFoundException extends ApiException {
    public TokenNotFoundException(ErrorType errorType) {
        super(errorType);
    }
}

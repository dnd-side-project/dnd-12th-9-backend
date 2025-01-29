package com.dnd.sbooky.api.security;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class InvalidSignatureException extends ApiException {
    public InvalidSignatureException(ErrorType errorType) {
        super(errorType);
    }
}

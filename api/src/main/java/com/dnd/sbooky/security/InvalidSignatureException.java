package com.dnd.sbooky.security;

import com.dnd.sbooky.support.error.ApiException;
import com.dnd.sbooky.support.error.ErrorType;

public class InvalidSignatureException extends ApiException {
    public InvalidSignatureException(ErrorType errorType) {
        super(errorType);
    }
}

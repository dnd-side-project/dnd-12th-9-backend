package com.dnd.book.security;

import com.dnd.book.support.error.ApiException;
import com.dnd.book.support.error.ErrorType;

public class InvalidSignatureException extends ApiException {
    public InvalidSignatureException(ErrorType errorType) {
        super(errorType);
    }
}

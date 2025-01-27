package com.dnd.book.security;

import com.dnd.book.support.error.ApiException;
import com.dnd.book.support.error.ErrorType;

public class InvalidTokenException extends ApiException {
    public InvalidTokenException(ErrorType errorType) {
        super(errorType);
    }
}

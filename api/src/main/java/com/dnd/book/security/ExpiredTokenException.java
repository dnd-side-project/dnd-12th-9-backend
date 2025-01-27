package com.dnd.book.security;

import com.dnd.book.support.error.ApiException;
import com.dnd.book.support.error.ErrorType;

public class ExpiredTokenException extends ApiException {
    public ExpiredTokenException(ErrorType errorType) {
        super(errorType);
    }
}

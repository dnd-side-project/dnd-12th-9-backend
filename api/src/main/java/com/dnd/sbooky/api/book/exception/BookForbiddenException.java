package com.dnd.sbooky.api.book.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class BookForbiddenException extends ApiException {

    public BookForbiddenException(ErrorType errorType) {
        super(errorType);
    }
}

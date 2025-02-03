package com.dnd.sbooky.api.book.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class BookNotFoundException extends ApiException {

    public BookNotFoundException(ErrorType errorType) {
        super(errorType);
    }
}

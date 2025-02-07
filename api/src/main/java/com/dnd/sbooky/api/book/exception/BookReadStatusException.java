package com.dnd.sbooky.api.book.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class BookReadStatusException extends ApiException {

    public BookReadStatusException(ErrorType errorType) {
        super(errorType);
    }
}

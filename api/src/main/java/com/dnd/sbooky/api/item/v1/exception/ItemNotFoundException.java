package com.dnd.sbooky.api.item.v1.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class ItemNotFoundException extends ApiException {
    public ItemNotFoundException(ErrorType errorType) {
        super(errorType);
    }
}

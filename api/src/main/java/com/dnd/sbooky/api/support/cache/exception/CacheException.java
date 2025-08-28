package com.dnd.sbooky.api.support.cache.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class CacheException extends ApiException {

    public CacheException(ErrorType errorType) {
        super(errorType);
    }
}

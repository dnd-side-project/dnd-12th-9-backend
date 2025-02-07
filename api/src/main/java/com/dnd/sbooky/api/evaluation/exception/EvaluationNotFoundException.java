package com.dnd.sbooky.api.evaluation.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;

public class EvaluationNotFoundException extends ApiException {

    public EvaluationNotFoundException(ErrorType errorType) {
        super(errorType);
    }
}

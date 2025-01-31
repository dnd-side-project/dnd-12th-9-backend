package com.dnd.sbooky.api.book.exception;

import com.dnd.sbooky.api.support.error.ApiException;
import com.dnd.sbooky.api.support.error.ErrorType;


public class MemberNotFoundException extends ApiException {

	public MemberNotFoundException(ErrorType errorType) {
		super(errorType);
	}
}

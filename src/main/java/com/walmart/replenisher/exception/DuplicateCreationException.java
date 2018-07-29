package com.walmart.replenisher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DuplicateCreationException extends RuntimeException {
	 public DuplicateCreationException(String message) {
		super(message);
	}
}

package com.walmart.replenisher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserNotAnAdminException extends RuntimeException {
	
	public UserNotAnAdminException(String message) {
		super(message);
	}
}

package com.walmart.replenisher.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.walmart.replenisher.entity.ErrorDetails;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicateCreationException.class) 
	public final ResponseEntity<ErrorDetails> handleDuplicateCreationException(DuplicateCreationException e, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(InvalidRoleException.class) 
	public final ResponseEntity<ErrorDetails> handleInvalidRoleException(InvalidRoleException e, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotAnAdminException.class) 
	public final ResponseEntity<ErrorDetails> handleUserNotAnAdminException(UserNotAnAdminException e, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class) 
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	 
	@ExceptionHandler(UserNotABusinessException.class) 
	public final ResponseEntity<ErrorDetails> handleUserNotABusinessException(UserNotABusinessException e, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
}

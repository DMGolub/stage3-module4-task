package com.mjc.school.controller.exceptionhandler;

import com.mjc.school.service.exception.EntityNotFoundException;
import com.mjc.school.service.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {IllegalArgumentException.class})
	protected ResponseEntity<Object> handleIllegalArgumentException(
		final IllegalArgumentException e,
		final WebRequest request
	) {
		return handleExceptionInternal(e, e.getMessage(),
			new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = {ValidationException.class})
	protected ResponseEntity<Object> handleValidationException(
		final ValidationException e,
		final WebRequest request
	) {
		return handleExceptionInternal(e, e.getMessage(),
			new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
	}

	@ExceptionHandler(value = {EntityNotFoundException.class})
	protected ResponseEntity<Object> handleEntityNotFoundException(
		final EntityNotFoundException e,
		final WebRequest request
	) {
		return handleExceptionInternal(e, e.getMessage(),
			new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<Object> handleException(
		final EntityNotFoundException e,
		final WebRequest request
	) {
		return handleExceptionInternal(e, e.getMessage(),
			new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
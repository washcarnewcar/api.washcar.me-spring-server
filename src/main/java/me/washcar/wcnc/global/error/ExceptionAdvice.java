package me.washcar.wcnc.global.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorResponse HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
		return ErrorResponse
			.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage())
			.title(exception.getClass().getSimpleName())
			.build();
	}

	/**
	 * 405 Method not allowed Exception Handler
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ErrorResponse HttpRequestMethodNotSupportedExceptionHandler(
		HttpRequestMethodNotSupportedException exception) {
		return ErrorResponse
			.builder(exception, HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage())
			.title(exception.getClass().getSimpleName())
			.build();
	}

	/**
	 * validation exception handler
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
		List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();
		List<String> errors = new ArrayList<>();
		for (ObjectError objectError : objectErrors) {
			errors.add(objectError.getDefaultMessage());
		}
		String error = String.join("\n", errors);
		return ErrorResponse
			.builder(exception, HttpStatus.BAD_REQUEST, error)
			.title(exception.getClass().getSimpleName())
			.build();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorResponse constraintViolationExceptionHandler(ConstraintViolationException exception) {
		return ErrorResponse
			.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage())
			.title(exception.getClass().getSimpleName())
			.build();
	}

	@ExceptionHandler(BusinessException.class)
	public ErrorResponse businessExceptionHandler(BusinessException exception) {
		BusinessError businessError = exception.getBusinessError();
		return ErrorResponse
			.builder(exception, businessError.getHttpStatus(), businessError.getMessage())
			.title(exception.getClass().getSimpleName())
			.build();
	}

	@ExceptionHandler
	public ErrorResponse generalExceptionHandler(Exception exception) {
		return ErrorResponse
			.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage())
			.title(exception.getClass().getSimpleName())
			.build();
	}

}

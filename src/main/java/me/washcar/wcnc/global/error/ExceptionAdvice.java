package me.washcar.wcnc.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
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

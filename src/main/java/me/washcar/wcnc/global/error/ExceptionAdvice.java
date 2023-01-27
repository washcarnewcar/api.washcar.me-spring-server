package me.washcar.wcnc.global.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
                .title("ConstraintViolationException")
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse businessExceptionHandler(BusinessException exception) {
        BusinessError businessError = exception.getBusinessError();
        return ErrorResponse
                .builder(exception, businessError.getHttpStatus(), businessError.getMessage())
                .title("BusinessException")
                .build();
    }
}

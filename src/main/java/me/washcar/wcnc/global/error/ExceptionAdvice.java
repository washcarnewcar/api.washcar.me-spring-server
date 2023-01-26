package me.washcar.wcnc.global.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return ErrorResponse
                .builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage())
                .title("ConstraintViolationException")
                .build();
    }
}

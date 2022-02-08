package com.mob.casestudy.digitalbanking.exceptionhandler;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;

import com.mob.casestudy.digitalbanking.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public final ResponseEntity<Object> handleCustomerNotFoundException(CustomNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrorCode(), ex.getErrorDescription());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public final ResponseEntity<Object> handleCustomBadRequestException(CustomBadRequestException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrorCode(), ex.getErrorDescription());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_VALIDATE_ERROR, Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}

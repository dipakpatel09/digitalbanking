package com.mob.casestudy.digitalbanking.exceptionhandler;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.mob.casestudy.digitalbanking.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class CustomizedExceptionResponseHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_VALIDATE_ERROR, "Security question id or answer can't be Null");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_CREATE_FIELD_ERROR, "Security question Id not valid");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        if (Objects.requireNonNull(ex.getMessage()).contains("PreferredLanguage")) {
            exceptionResponse.setErrorCode(PREF_LANG_INVALID_ERROR);
            exceptionResponse.setErrorDescription("Preferred Language Invalid");
        } else if (request.getDescription(false).equals("uri=/customer-service/client-api/v1/customers")) {
            exceptionResponse.setErrorCode(MANDATORY_FIELD_ERROR);
            exceptionResponse.setErrorDescription("All mandatory field should be validated.");
        } else {
            exceptionResponse.setErrorCode(CUS_SEC_QUES_CREATE_FIELD_ERROR);
            exceptionResponse.setErrorDescription("Security question answer is not in proper format.");
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        if (request.getDescription(false).equals("uri=/customer-service/client-api/v1/customers")) {
            exceptionResponse.setErrorCode(MANDATORY_FIELD_ERROR);
            exceptionResponse.setErrorDescription("Null not accepted");
        } else {
            exceptionResponse.setErrorCode(OTP_IS_NULL_OR_EMPTY);
            exceptionResponse.setErrorDescription("OTP must be of 6 digits");
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}

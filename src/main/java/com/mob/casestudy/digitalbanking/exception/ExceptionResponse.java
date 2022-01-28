package com.mob.casestudy.digitalbanking.exception;

public class ExceptionResponse {

    private String errorCode;
    private String errorDescription;

    public ExceptionResponse(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}

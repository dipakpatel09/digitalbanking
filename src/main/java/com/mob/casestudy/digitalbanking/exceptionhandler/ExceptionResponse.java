package com.mob.casestudy.digitalbanking.exceptionhandler;

public class ExceptionResponse {

    private String errorCode;
    private String errorDescription;

    public ExceptionResponse() {
    }

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

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}

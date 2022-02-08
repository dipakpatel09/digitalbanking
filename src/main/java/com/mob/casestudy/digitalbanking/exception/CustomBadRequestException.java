package com.mob.casestudy.digitalbanking.exception;

public class CustomBadRequestException extends RuntimeException {
    private final String errorCode;
    private final String errorDescription;

    public CustomBadRequestException(String errorCode, String errorDescription) {
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

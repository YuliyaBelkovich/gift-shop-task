package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionDefinition {
    IDENTITY_NOT_FOUND(40401, "Identity not found", HttpStatus.NOT_FOUND),
    IDENTITY_ALREADY_EXISTS(40901, "Identity already exists", HttpStatus.CONFLICT);


    private final int errorCode;
    private final String message;
    private final HttpStatus status;

    ExceptionDefinition(int errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}


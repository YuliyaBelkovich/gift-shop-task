package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionManager {
    IDENTITY_NOT_FOUND(4040, "Identity not found", HttpStatus.NOT_FOUND),
    IDENTITY_ALREADY_EXISTS(4041, "Identity already exists", HttpStatus.CONFLICT);


    private final int errorCode;
    private final String message;
    private final HttpStatus status;

    ExceptionManager(int errorCode, String message, HttpStatus status) {
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


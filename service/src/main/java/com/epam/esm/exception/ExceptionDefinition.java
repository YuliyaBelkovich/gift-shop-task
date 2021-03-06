package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionDefinition {

    IDENTITY_NOT_FOUND(40401, HttpStatus.NOT_FOUND, "Identity not found"),
    IDENTITY_ALREADY_EXISTS(40901, HttpStatus.CONFLICT, "Identity already exists"),
    INTERNAL_SERVER_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "Exception on the server side occurred. Please try later"),
    BAD_REQUEST(40002, HttpStatus.BAD_REQUEST, "Unable to read request"),
    INVALID_MEDIA_TYPE(41501, HttpStatus.UNSUPPORTED_MEDIA_TYPE,"This type of information can not be processed by application"),
    METHOD_NOT_SUPPORTED(40501, HttpStatus.METHOD_NOT_ALLOWED, "API doesn't provide such method");

    private final int errorCode;
    private final HttpStatus status;
    private final String[] messages;

    ExceptionDefinition(int errorCode, HttpStatus status, String... messages) {
        this.errorCode = errorCode;
        this.messages = messages;
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String[] getMessages() {
        return messages;
    }

    public HttpStatus getStatus() {
        return status;
    }
}


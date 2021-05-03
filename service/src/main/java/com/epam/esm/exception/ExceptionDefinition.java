package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionDefinition {

    IDENTITY_NOT_FOUND(40401, HttpStatus.NOT_FOUND, "Identity not found"),
    IDENTITY_ALREADY_EXISTS(40001, HttpStatus.BAD_REQUEST, "Identity already exists"),
    UNSUPPORTED_MEDIA_TYPE(41501, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Application consumes only application/json content-type"),
    TAG_UPDATE_OPERATION_NOT_SPECIFIED(40003, HttpStatus.BAD_REQUEST, "Tag operations not specified. Please, specify action by adding + or - before tag name"),
    INTERNAL_SERVER_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "Exception on the server side occurred. Please try later"),
    MISSING_PATH_VARIABLE(40002, HttpStatus.BAD_REQUEST, "Unable to read request"),
    METHOD_NOT_SUPPORTED(40501, HttpStatus.METHOD_NOT_ALLOWED, "API doesn't provide such method"),
    UNABLE_TO_FIND_MOST_POPULAR_TAG(40402, HttpStatus.NOT_FOUND, "Unable to find most recently used tag");

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


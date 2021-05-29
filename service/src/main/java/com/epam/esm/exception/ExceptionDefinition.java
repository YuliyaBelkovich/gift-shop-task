package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionDefinition {

    IDENTITY_NOT_FOUND(40401, HttpStatus.NOT_FOUND, "identity.not.found"),
    IDENTITY_ALREADY_EXISTS(40001, HttpStatus.BAD_REQUEST, "identity.already.exists"),
    UNSUPPORTED_MEDIA_TYPE(41501, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "wrong.media.type"),
    TAG_UPDATE_OPERATION_NOT_SPECIFIED(40003, HttpStatus.BAD_REQUEST, "tag.operation"),
    FILTER_TAG_FORMAT(4004, HttpStatus.BAD_REQUEST, "tag.filter"),
    SORTING_FORMAT(4005, HttpStatus.BAD_REQUEST, "sort.format"),
    REGISTER_EMAIL(4006, HttpStatus.BAD_REQUEST, "email.taken"),
    REGISTER_NAME(4007, HttpStatus.BAD_REQUEST, "username.taken"),
    INTERNAL_SERVER_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "internal.error"),
    MISSING_PATH_VARIABLE(40002, HttpStatus.BAD_REQUEST, "bad.request"),
    METHOD_NOT_SUPPORTED(40501, HttpStatus.METHOD_NOT_ALLOWED, "api.method"),
    UNABLE_TO_FIND_MOST_POPULAR_TAG(40402, HttpStatus.NOT_FOUND, "popular.tag");

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


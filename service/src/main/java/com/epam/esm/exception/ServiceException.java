package com.epam.esm.exception;

public class ServiceException extends RuntimeException {

    private ExceptionDefinition exceptionDefinition;

    public ServiceException(ExceptionDefinition exceptionDefinition) {
        this.exceptionDefinition = exceptionDefinition;
    }

    public ExceptionDefinition getExceptionDefinition() {
        return exceptionDefinition;
    }
}

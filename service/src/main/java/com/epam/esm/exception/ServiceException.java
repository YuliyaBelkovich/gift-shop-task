package com.epam.esm.exception;

public class ServiceException extends RuntimeException {
    private ExceptionManager exceptionManager;

    public ServiceException(ExceptionManager exceptionManager) {
        this.exceptionManager = exceptionManager;
    }

    public ExceptionManager getExceptionManager() {
        return exceptionManager;
    }
}

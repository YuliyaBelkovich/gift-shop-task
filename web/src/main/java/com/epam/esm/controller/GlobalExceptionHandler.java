package com.epam.esm.controller;

import com.epam.esm.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleException(ServiceException e) {
        return new ResponseEntity<>(new GiftShopErrorResponse(e.getExceptionManager().getErrorCode(), e.getExceptionManager().getMessage()), e.getExceptionManager().getStatus());
    }
}

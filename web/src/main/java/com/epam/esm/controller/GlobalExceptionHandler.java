package com.epam.esm.controller;

import com.epam.esm.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleServiceException(ServiceException e) {
        return new ResponseEntity<>(new GiftShopErrorResponse(e.getExceptionManager().getErrorCode(), e.getExceptionManager().getMessage()), e.getExceptionManager().getStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleValidationException(ValidationException e, BindingResult bindingResult) {
        return null;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GiftShopErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(new GiftShopErrorResponse(500, "Exception on the server side occurred. Please try later"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

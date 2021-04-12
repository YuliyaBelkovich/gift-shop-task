package com.epam.esm.controller;

import com.epam.esm.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleServiceException(ServiceException e) {
        return ResponseEntity
                .status(e.getExceptionManager().getStatus())
                .body(new GiftShopErrorResponse(e.getExceptionManager().getErrorCode(), e.getExceptionManager().getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleValidationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GiftShopErrorResponse(4000, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GiftShopErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GiftShopErrorResponse(500, "Exception on the server side occurred. Please try later"));
    }

}

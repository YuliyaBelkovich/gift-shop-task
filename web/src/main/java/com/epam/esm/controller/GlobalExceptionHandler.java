package com.epam.esm.controller;

import com.epam.esm.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleServiceException(ServiceException e) {
        return new ResponseEntity<>(new GiftShopErrorResponse(e.getExceptionManager().getErrorCode(), e.getExceptionManager().getMessage()), e.getExceptionManager().getStatus());
    }

    //todo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleValidationException(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                                 HttpStatus status, WebRequest request) {
        return null;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GiftShopErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(new GiftShopErrorResponse(500, "Exception on the server side occurred. Please try later"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

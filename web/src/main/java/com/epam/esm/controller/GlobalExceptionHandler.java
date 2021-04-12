package com.epam.esm.controller;

import com.epam.esm.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleServiceException(ServiceException e) {
        List<String> messages = new ArrayList<>();
        messages.add(e.getMessage());
        return ResponseEntity
                .status(e.getExceptionManager().getStatus())
                .body(new GiftShopErrorResponse(e.getExceptionManager().getErrorCode(), messages));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<GiftShopErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            messages.add(error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GiftShopErrorResponse(40001, messages));

    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GiftShopErrorResponse> handleException(Exception e) {
        List<String> messages = new ArrayList<>();
        messages.add("Exception on the server side occurred. Please try later");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GiftShopErrorResponse(500, messages));
    }

}

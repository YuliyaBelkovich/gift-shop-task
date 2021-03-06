package com.epam.esm.controller;

import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createErrorResponse(ExceptionDefinition.METHOD_NOT_SUPPORTED);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createErrorResponse(ExceptionDefinition.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            messages.add(error.getDefaultMessage());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GiftShopErrorResponse(40001, messages));
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleServiceException(ServiceException e) {
        return createErrorResponse(e.getExceptionDefinition());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    protected ResponseEntity<Object> handleSqlException(BadSqlGrammarException e){
        return createErrorResponse(ExceptionDefinition.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return createErrorResponse(ExceptionDefinition.INVALID_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        return createErrorResponse(ExceptionDefinition.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> createErrorResponse(ExceptionDefinition exceptionDefinition) {
        return ResponseEntity
                .status(exceptionDefinition.getStatus())
                .body(new GiftShopErrorResponse(exceptionDefinition.getErrorCode(),
                        Arrays.asList(exceptionDefinition.getMessages())));
    }
}

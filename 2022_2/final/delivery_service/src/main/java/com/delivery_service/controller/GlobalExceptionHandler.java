package com.delivery_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.delivery_service.dto.ApiError;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleBaseExceptions(Exception ex) {
        var errors = ex.getMessage();
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException ex) {
        var error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.warn(">>> handle: e.getMethod() is not support for request");
        var builder = new StringBuilder();
        builder.append(e.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(e.getSupportedHttpMethods())
                .forEach(m -> builder.append(m).append(" "));

        var apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage(), builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

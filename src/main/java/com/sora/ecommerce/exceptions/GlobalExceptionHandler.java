package com.sora.ecommerce.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sora.ecommerce.constants.ResponseStatus;
import com.sora.ecommerce.response.ResponseHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException exception) {
        String error = exception.getMessage();
        HttpStatus statusCode = exception.getStatusCode();

        return ResponseHandler.responseBuilder(statusCode, ResponseStatus.FAIL, error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidRequestException(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();
        exception.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        HttpStatus statusCode = (HttpStatus) exception.getStatusCode();

        return ResponseHandler.responseBuilder(statusCode, ResponseStatus.FAIL, errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGlobalException(RuntimeException exception) {
        String error = exception.getMessage();

        return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAIL, error);
    }

}

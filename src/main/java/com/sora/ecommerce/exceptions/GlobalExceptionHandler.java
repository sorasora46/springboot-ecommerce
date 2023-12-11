package com.sora.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sora.ecommerce.constants.ResponseStatus;
import com.sora.ecommerce.response.ResponseHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleException(ApiException exception) {
        String error = exception.getMessage();
        HttpStatus statusCode = exception.getStatusCode();

        return ResponseHandler.responseBuilder(statusCode, ResponseStatus.FAIL, error);
    }

}

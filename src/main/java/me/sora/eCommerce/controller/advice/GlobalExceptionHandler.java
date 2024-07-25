package me.sora.eCommerce.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleInternalServerError(Exception ex) {
        // Log the exception or handle it accordingly
        return new ResponseEntity<>("Internal Server Error: " + Arrays.toString(ex.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package me.sora.eCommerce.controller.advice;

import io.jsonwebtoken.JwtException;
import me.sora.eCommerce.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.FAILED;
import static me.sora.eCommerce.constant.ErrorConstant.AUTHENTICATION_ERROR;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleInternalServerError(Exception ex) {
        var response = CommonResponse.of(FAILED, ex.getMessage());
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleNoResourceFoundException(NoResourceFoundException ex) {
        var response = CommonResponse.of(FAILED, ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler({AuthenticationException.class, JwtException.class})
    public ResponseEntity<CommonResponse<String>> handleAuthenticationException(Exception ex) {
        var exceptionMessage = ex.getMessage();
        var responseMessage = exceptionMessage == null ? AUTHENTICATION_ERROR : exceptionMessage;

        if (ex instanceof UsernameNotFoundException) {
            responseMessage = "Username `" + ex.getMessage() + "` Not Found";
        }

        var response = CommonResponse.of(FAILED, responseMessage);
        return new ResponseEntity<>(response, UNAUTHORIZED);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<String>> handleCustomException(CustomException ex) {
        var response = CommonResponse.of(FAILED, ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<ValidationErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> ValidationErrorResponse.ValidationError.builder()
                        .field(field.getField())
                        .message(field.getDefaultMessage())
                        .providedValue(Objects.toString(field.getRejectedValue()))
                        .build())
                .toList();
        var response = CommonResponse.of(FAILED, ValidationErrorResponse.builder().errors(errors).build());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

}
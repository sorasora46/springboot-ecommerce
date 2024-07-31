package me.sora.eCommerce.controller.advice;

import me.sora.eCommerce.dto.CommonResponse;
import me.sora.eCommerce.dto.Validation.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.FAILED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleInternalServerError(Exception ex) {
        var response = CommonResponse.of(FAILED, ex.getMessage());
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        var response = CommonResponse.of(FAILED, ex.getMessage());
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
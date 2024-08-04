package me.sora.eCommerce.controller.advice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@Setter
public class CustomException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;

    public CustomException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}

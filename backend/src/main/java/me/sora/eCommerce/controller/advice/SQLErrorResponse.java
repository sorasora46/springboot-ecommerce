package me.sora.eCommerce.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SQLErrorResponse {
    private String message;
    private Integer sqlErrorCode;
    private String sqlState;
}

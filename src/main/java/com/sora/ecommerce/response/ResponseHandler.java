package com.sora.ecommerce.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(HttpStatus statusCode, Boolean success, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", statusCode.value());
        response.put("success", success);

        String dataKey = success ? "data" : "errors";
        response.put(dataKey, data);

        return new ResponseEntity<>(response, statusCode);
    }
}

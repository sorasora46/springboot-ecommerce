package me.sora.eCommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private boolean success;
    private T result;

    public static <T> CommonResponse<T> of(boolean success, T result) {
        return CommonResponse.<T>builder()
                .success(success)
                .result(result)
                .build();
    }
}
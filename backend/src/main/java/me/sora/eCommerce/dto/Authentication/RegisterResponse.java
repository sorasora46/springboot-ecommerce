package me.sora.eCommerce.dto.Authentication;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RegisterResponse {
    private String accessToken;
    private String refreshToken;
    private String id;
    private Instant createdDate;
}

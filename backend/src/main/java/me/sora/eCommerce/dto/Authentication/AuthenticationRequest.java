package me.sora.eCommerce.dto.Authentication;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}

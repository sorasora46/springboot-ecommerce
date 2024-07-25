package me.sora.eCommerce.dto.Authentication;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private LocalDate birthDate;
    private String role;
}

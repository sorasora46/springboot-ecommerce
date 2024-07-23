package me.sora.eCommerce.dto.User;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class GetUserResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private LocalDate birthDate;
    private String role;
    private Instant createdDate;
    private Instant updatedDate;
}

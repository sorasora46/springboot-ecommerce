package me.sora.eCommerce.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
public class GetUserResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Date birthDate;
    private String role;
    private Instant createdDate;
    private Instant updatedDate;
}

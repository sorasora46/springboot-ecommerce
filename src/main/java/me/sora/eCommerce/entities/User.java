package me.sora.eCommerce.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
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

package com.sora.ecommerce.auth;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "credentials")
public class UserCredential {

    @Id
    private UUID userId;
    private String username;
    private String hashedPassword;

    public UserCredential() {
    }

    public UserCredential(UUID userId, String username, String hashedPassword) {
        this.userId = userId;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}

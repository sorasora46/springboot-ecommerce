package com.sora.ecommerce.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserPayload {

    @NotBlank(message = "first name is required.")
    @Size(min = 2, max = 50, message = "the first name must be from 2 to 50 characters.")
    private String firstName;

    @NotBlank(message = "last name is required.")
    @Size(min = 2, max = 50, message = "the last name must be from 2 to 50 characters.")
    private String lastName;

    @NotBlank(message = "email is required.")
    @Email(message = "email is not a valid email.")
    private String email;

    private String username;
    private String password;

    public CreateUserPayload() {
    }

    public CreateUserPayload(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

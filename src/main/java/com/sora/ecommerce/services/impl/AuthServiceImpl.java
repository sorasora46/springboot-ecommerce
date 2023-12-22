package com.sora.ecommerce.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.sora.ecommerce.auth.UserCredential;
import com.sora.ecommerce.exceptions.ApiException;
import com.sora.ecommerce.repositories.UserCredentialRepository;
import com.sora.ecommerce.services.AuthService;

public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    public AuthServiceImpl() {
    }

    @Override
    public UserCredential getUserCredentialById(UUID userId) {
        Optional<UserCredential> credential = userCredentialRepository.findById(userId);

        if (credential.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "User id: " + userId + " not found");

        return credential.get();
    }

    @Override
    public String getHashedPasswordById(UUID userId) {
        Optional<String> hashedPassword = userCredentialRepository.getHashedPasswordById(userId);

        if (hashedPassword.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "User id: " + userId + " not found");

        return hashedPassword.get();
    }

    @Override
    public Boolean registerCredential(UUID userId, String username, String hashedPassword) {
        try {
            UserCredential newCredential = new UserCredential(userId, username, hashedPassword);

            userCredentialRepository.save(newCredential);

            return true;
        } catch (RuntimeException exception) {
            throw new ApiException(exception.getMessage());
        }
    }

}

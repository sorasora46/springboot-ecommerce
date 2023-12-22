package com.sora.ecommerce.services;

import java.util.UUID;

import com.sora.ecommerce.auth.UserCredential;

public interface AuthService {

    public Boolean registerCredential(UUID userId, String username, String hashedPassword);

    public UserCredential getUserCredentialById(UUID userId);

    public String getHashedPasswordById(UUID userId);

}

package com.sora.ecommerce.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sora.ecommerce.auth.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {

    @Query(value = "SELECT hashed_password FROM credentials WHERE user_id = ?1", nativeQuery = true)
    public Optional<String> getHashedPasswordById(UUID userId);

}

package com.sora.ecommerce.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sora.ecommerce.models.domains.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE first_name = ?1 AND last_name = ?2", nativeQuery = true)
    public Optional<User> findByName(String firstName, String lastName);

    public Boolean existsByEmail(String email);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE first_name = ?1 AND last_name = ?2)", nativeQuery = true)
    public Boolean existsByName(String firstName, String lastName);

}

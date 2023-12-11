package com.sora.ecommerce.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sora.ecommerce.models.domains.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}

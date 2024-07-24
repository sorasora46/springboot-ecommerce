package me.sora.eCommerce.repository;

import me.sora.eCommerce.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CredentialRepository extends JpaRepository<Credential, String> { }

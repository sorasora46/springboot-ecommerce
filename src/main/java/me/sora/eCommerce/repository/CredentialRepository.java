package me.sora.eCommerce.repository;

import me.sora.eCommerce.entity.Credential;
import me.sora.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CredentialRepository extends JpaRepository<Credential, String> {

    @Query(value = "SELECT c.salt FROM credentials c WHERE c.user = :user")
    Optional<String> findSaltByUserId(@Param("user") User user);

}

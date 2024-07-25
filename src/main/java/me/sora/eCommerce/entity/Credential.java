package me.sora.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.sora.eCommerce.constant.AuthConstant;

import java.time.Instant;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", nullable = false, length = AuthConstant.SALT_LENGTH)
    private String salt;

    @Column(name = "updated_date", nullable = false)
    private Instant updatedDate;

    @PreUpdate
    protected void onUpdate() {
        updatedDate = Instant.now();
    }

}

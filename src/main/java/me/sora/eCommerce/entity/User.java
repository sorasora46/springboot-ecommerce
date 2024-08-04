package me.sora.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.sora.eCommerce.constant.AuthConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_no", unique = true, nullable = false, length = 15)
    private String phoneNo;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private AuthConstant.Role role;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Credential credential;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @PrePersist
    protected void onCreate() {
        var now = Instant.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = Instant.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return credential != null ? credential.getPassword() : null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

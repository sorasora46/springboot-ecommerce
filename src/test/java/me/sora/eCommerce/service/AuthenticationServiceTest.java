package me.sora.eCommerce.service;

import me.sora.eCommerce.constant.AuthConstant;
import me.sora.eCommerce.dto.Authentication.AuthenticationRequest;
import me.sora.eCommerce.dto.Authentication.RegisterRequest;
import me.sora.eCommerce.entity.Credential;
import me.sora.eCommerce.entity.User;
import me.sora.eCommerce.repository.CredentialRepository;
import me.sora.eCommerce.repository.UserRepository;
import me.sora.eCommerce.util.AuthUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthUtils authUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void givenRequest_whenAuthenticate_thenSuccess() {
        // Given
        var username = "username";
        var password = "password";
        var salt = "salt";
        var saltedPassword = password + salt;
        var user = new User();
        var token = "token";

        var request = AuthenticationRequest.builder()
                .username(username)
                .password(saltedPassword)
                .build();

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(credentialRepository.findSaltByUserId(any(User.class))).thenReturn(Optional.of(salt));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(new UsernamePasswordAuthenticationToken(username, saltedPassword));
        when(authUtils.generateToken(any(User.class))).thenReturn(token);

        // Then
        var response = authenticationService.authenticate(request);
        assertNotNull(response);
        assertEquals(response.getToken(), token);
    }

    @Test
    void givenRequest_whenAuthenticate_findByUsername_thenUsernameNotFound() {
        // Given
        var username = "username";
        var password = "password";
        var salt = "salt";
        var saltedPassword = password + salt;

        var request = AuthenticationRequest.builder()
                .username(username)
                .password(saltedPassword)
                .build();

        // When
        when(userRepository.findByUsername(anyString())).thenThrow(new UsernameNotFoundException(username));

        // Then
        var exception = assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(request));
        assertNotNull(exception);
        assertEquals(exception.getMessage(), username);
    }

    @Test
    void givenRequest_whenAuthenticate_findSaltByUserId_thenUsernameNotFound() {
        // Given
        var username = "username";
        var password = "password";
        var salt = "salt";
        var saltedPassword = password + salt;
        var user = new User();

        var request = AuthenticationRequest.builder()
                .username(username)
                .password(saltedPassword)
                .build();

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(credentialRepository.findSaltByUserId(any(User.class))).thenThrow(new UsernameNotFoundException(username));

        // Then
        var exception = assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(request));
        assertNotNull(exception);
        assertEquals(exception.getMessage(), username);
    }

    @Test
    void givenRequest_whenRegister_thenSuccess() {
        // Given
        var request = RegisterRequest.builder()
                .username("username")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .phoneNo("phoneNo")
                .birthDate(LocalDate.now())
                .role(AuthConstant.Role.USER.name())
                .build();
        var salt = "salt";
        var hashedPassword = "hashedPassword";
        var token = "token";
        var user = new User();
        user.setId("id");
        user.setCreatedDate(Instant.now());

        // When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(authUtils.generateSalt(anyInt())).thenReturn(salt);
        when(passwordEncoder.encode(anyString())).thenReturn(hashedPassword);
        when(credentialRepository.save(any(Credential.class))).thenReturn(new Credential());
        when(authUtils.generateToken(any(User.class))).thenReturn(token);

        // Then
        var response = authenticationService.register(request);
        assertNotNull(response);
        assertEquals(response.getToken(), token);
        assertEquals(response.getId(), user.getId());
        assertEquals(response.getCreatedDate(), user.getCreatedDate());
    }

}

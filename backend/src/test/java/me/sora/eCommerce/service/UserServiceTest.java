package me.sora.eCommerce.service;

import me.sora.eCommerce.constant.AuthConstant;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.entity.Credential;
import me.sora.eCommerce.entity.User;
import me.sora.eCommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setCredential(new Credential());
        user.setId("id");
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNo("phoneNo");
        user.setUsername("username");
        user.setRole(AuthConstant.Role.USER);
    }

    @Test
    void givenRequest_whenGetUserById_thenReturnUser() {
        // Given
        var now = Instant.now();
        var dateNow = LocalDate.now();
        var expectedResponse = GetUserResponse.builder()
                .id("id")
                .birthDate(dateNow)
                .role(AuthConstant.Role.USER.name())
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .phoneNo("phoneNo")
                .username("username")
                .createdDate(now)
                .updatedDate(now)
                .build();
        user.setBirthDate(dateNow);
        user.setCreatedDate(now);
        user.setUpdatedDate(now);

        // When
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        // Then
        var response = userService.getUserById("id");
        assertNotNull(response);
        assertEquals(response, expectedResponse);
    }

    @Test
    void givenRequest_whenGetUserById_thenDataNotFound() {
        // Given

        // When
        when(userRepository.findById(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        var exception = assertThrows(CustomException.class, () -> userService.getUserById("not exist id"));
        assertNotNull(exception);
        assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
        assertEquals(exception.getMessage(), ErrorConstant.DATA_NOT_FOUND);
    }

    @Test
    void givenRequest_whenGetUserByUsername_thenReturnUser() {
        // Given
        var now = Instant.now();
        var dateNow = LocalDate.now();
        var expectedResponse = GetUserResponse.builder()
                .id("id")
                .birthDate(dateNow)
                .role(AuthConstant.Role.USER.name())
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .phoneNo("phoneNo")
                .username("username")
                .createdDate(now)
                .updatedDate(now)
                .build();
        user.setBirthDate(dateNow);
        user.setCreatedDate(now);
        user.setUpdatedDate(now);

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // Then
        var response = userService.getUserByUsername("username");
        assertNotNull(response);
        assertEquals(response, expectedResponse);
    }

    @Test
    void givenRequest_whenGetUserByUsername_thenDataNotFound() {
        // Given

        // When
        when(userRepository.findByUsername(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        var exception = assertThrows(CustomException.class, () -> userService.getUserByUsername("not exist username"));
        assertNotNull(exception);
        assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
        assertEquals(exception.getMessage(), ErrorConstant.DATA_NOT_FOUND);
    }

}

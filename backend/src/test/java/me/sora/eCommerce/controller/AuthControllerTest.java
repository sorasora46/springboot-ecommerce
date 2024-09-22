package me.sora.eCommerce.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.sora.eCommerce.config.AuthenticationFilter;
import me.sora.eCommerce.constant.AuthConstant;
import me.sora.eCommerce.dto.Authentication.AuthenticationRequest;
import me.sora.eCommerce.dto.Authentication.AuthenticationResponse;
import me.sora.eCommerce.dto.Authentication.RegisterRequest;
import me.sora.eCommerce.dto.Authentication.RegisterResponse;
import me.sora.eCommerce.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDate;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.FAILED;
import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AuthController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthenticationFilter.class}))
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenRequest_whenRegister_thenSuccess() throws Exception {
        // Given
        var now = Instant.now();
        var request = RegisterRequest.builder()
                .username("username")
                .password("password")
                .role(AuthConstant.Role.USER.name())
                .birthDate(LocalDate.now())
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .phoneNo("phoneNo")
                .build();
        var response = RegisterResponse.builder()
                .id("id")
                .createdDate(now)
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        // When
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(response.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.createdDate").value(response.getCreatedDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accessToken").value(response.getAccessToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.refreshToken").value(response.getRefreshToken()));
    }

    @Test
    void givenInvalidRequest_whenRegister_thenBadRequestError() throws Exception {
        // Given
        var request = RegisterRequest.builder().build();

        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(FAILED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errors").isNotEmpty());
    }

    @Test
    void givenRequest_whenLogin_thenSuccess() throws Exception {
        // Given
        var now = Instant.now();
        var request = AuthenticationRequest.builder()
                .username("username")
                .password("password")
                .build();
        var response = AuthenticationResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .loggedInDate(now)
                .build();

        // When
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.accessToken").value(response.getAccessToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.refreshToken").value(response.getRefreshToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.loggedInDate").value(now.toString()));
    }

    @Test
    void givenInvalidRequest_whenLogin_thenBadRequestError() throws Exception {
        // Given
        var request = AuthenticationRequest.builder().build();

        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(FAILED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errors").isNotEmpty());
    }

}

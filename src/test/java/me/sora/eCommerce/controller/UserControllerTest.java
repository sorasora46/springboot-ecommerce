package me.sora.eCommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sora.eCommerce.config.AuthenticationFilter;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.entity.Credential;
import me.sora.eCommerce.entity.User;
import me.sora.eCommerce.mapper.UserMapper;
import me.sora.eCommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.LocalDate;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.FAILED;
import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;
import static me.sora.eCommerce.constant.AuthConstant.Role.USER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthenticationFilter.class}))
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenRequest_whenGetUserById_thenReturnUser() throws Exception {
        // Given
        var user = new User();
        var now = Instant.now();
        user.setId("id");
        user.setUsername("username");
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setBirthDate(LocalDate.now());
        user.setPhoneNo("phoneNo");
        user.setRole(USER);
        user.setCredential(new Credential());
        user.setCreatedDate(now);
        user.setUpdatedDate(now);
        var expectedResponse = UserMapper.INSTANCE.fromUserEntityToGetUserResponse(user);

        // When
        when(userService.getUserById(anyString())).thenReturn(expectedResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.id")
                        .value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.username")
                        .value(expectedResponse.getUsername()));
    }

    @Test
    void givenRequest_whenGetUserByUsername_thenReturnUser() throws Exception {
        // Given
        var user = new User();
        var now = Instant.now();
        user.setId("id");
        user.setUsername("username");
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setBirthDate(LocalDate.now());
        user.setPhoneNo("phoneNo");
        user.setRole(USER);
        user.setCredential(new Credential());
        user.setCreatedDate(now);
        user.setUpdatedDate(now);
        var expectedResponse = UserMapper.INSTANCE.fromUserEntityToGetUserResponse(user);

        // When
        when(userService.getUserByUsername(anyString())).thenReturn(expectedResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/username/{username}", user.getUsername())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.id")
                        .value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.username")
                        .value(expectedResponse.getUsername()));
    }

    @Test
    void givenRequest_whenGetUserById_thenReturnDataNotFoundException() throws Exception {
        // Given

        // When
        when(userService.getUserById(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{id}", "not found id")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(ErrorConstant.DATA_NOT_FOUND));
    }

    @Test
    void givenRequest_whenGetUserByUsername_thenReturnDataNotFoundException() throws Exception {
        // Given

        // When
        when(userService.getUserByUsername(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/username/{username}", "not found username")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(ErrorConstant.DATA_NOT_FOUND));
    }

}

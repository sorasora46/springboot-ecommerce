package me.sora.eCommerce.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.sora.eCommerce.config.AuthenticationFilter;
import me.sora.eCommerce.constant.ApiConstant;
import me.sora.eCommerce.dto.Cart.*;
import me.sora.eCommerce.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.List;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.FAILED;
import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CartController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthenticationFilter.class}))
@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenGetCart_thenReturnCart() throws Exception {
        // Given
        var response = GetCartResponse.builder()
                .cartItems(List.of())
                .build();

        // When
        when(cartService.getCart(anyString())).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/carts")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.cartItems")
                        .isArray());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenGetCart_thenServiceThrowException() throws Exception {
        // Given
        var errorMessage = "errorMessage";

        // When
        when(cartService.getCart(anyString())).thenThrow(new RuntimeException(errorMessage));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/carts")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(errorMessage));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenAddToCart_thenSuccess() throws Exception {
        // Given
        var productId = "productId";
        var quantity = 5;
        var addedDate = Instant.now();
        var request = AddProductToCartRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();
        var response = AddProductToCartResponse.builder()
                .addedDate(addedDate)
                .productId(productId)
                .quantity(quantity)
                .build();

        // When
        when(cartService.addToCart(any(AddProductToCartRequest.class), anyString())).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/carts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.addedDate")
                        .value(addedDate.toString()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.productId")
                        .value(productId))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.quantity")
                        .value(quantity));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenAddToCart_thenServiceThrowException() throws Exception {
        // Given
        var errorMessage = "errorMessage";
        var productId = "productId";
        var quantity = 5;
        var request = AddProductToCartRequest.builder()
                .productId(productId)
                .quantity(quantity)
                .build();

        // When
        when(cartService.addToCart(any(AddProductToCartRequest.class), anyString())).thenThrow(new RuntimeException(errorMessage));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/carts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(errorMessage));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenRemoveFromCart_thenSuccess() throws Exception {
        // Given
        var productId = "productId";
        var deletedDate = Instant.now();
        var response = RemoveProductFromCartResponse.builder()
                .deletedDate(deletedDate)
                .productId(productId)
                .build();

        // When
        when(cartService.removeFromCart(anyString(), anyString())).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/carts/{productId}", productId)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.deletedDate")
                        .value(deletedDate.toString()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.productId")
                        .value(productId));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenRemoveFromCart_thenServiceThrowException() throws Exception {
        // Given
        var errorMessage = "errorMessage";
        var productId = "productId";

        // When
        when(cartService.removeFromCart(anyString(), anyString())).thenThrow(new RuntimeException(errorMessage));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/carts/{productId}", productId)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(errorMessage));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenUpdateProductQuantityInCart_AndActionIsAdd_thenSuccess() throws Exception {
        // Given
        var productId = "productId";
        var updatedDate = Instant.now();
        var response = UpdateProductQuantityInCartResponse.builder()
                .productId(productId)
                .updatedDate(updatedDate)
                .build();

        // When
        when(cartService.updateProductQuantityInCart(anyString(), anyString(), anyString())).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/carts/{productId}", productId)
                        .param("action", ApiConstant.CartAction.ADD.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.productId")
                        .value(productId))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.updatedDate")
                        .value(updatedDate.toString()));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenUpdateProductQuantityInCart_AndActionIsRemove_thenSuccess() throws Exception {
        // Given
        var productId = "productId";
        var updatedDate = Instant.now();
        var response = UpdateProductQuantityInCartResponse.builder()
                .productId(productId)
                .updatedDate(updatedDate)
                .build();

        // When
        when(cartService.updateProductQuantityInCart(anyString(), anyString(), anyString())).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/carts/{productId}", productId)
                        .param("action", ApiConstant.CartAction.REMOVE.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.productId")
                        .value(productId))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.updatedDate")
                        .value(updatedDate.toString()));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenUpdateProductQuantityInCart_AndActionIsInvalid_thenThrowValidationException() throws Exception {
        // Given
        var productId = "productId";
        var invalidAction = "InvalidAction";

        // When

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/carts/{productId}", productId)
                        .param("action", invalidAction)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.errors")
                        .isArray())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.errors")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.errors[0].field")
                        .value("action"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.errors[0].providedValue")
                        .value(invalidAction));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenUpdateProductQuantityInCart_thenServiceThrowException() throws Exception {
        // Given
        var productId = "productId";
        var errorMessage = "errorMessage";

        // When
        when(cartService.updateProductQuantityInCart(anyString(), anyString(), anyString())).thenThrow(new RuntimeException(errorMessage));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/carts/{productId}", productId)
                        .param("action", ApiConstant.CartAction.ADD.name())
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(errorMessage));
    }
}

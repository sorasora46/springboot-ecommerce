package me.sora.eCommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sora.eCommerce.config.AuthenticationFilter;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Product.CreateProductRequest;
import me.sora.eCommerce.dto.Product.CreateProductResponse;
import me.sora.eCommerce.entity.Product;
import me.sora.eCommerce.mapper.ProductMapper;
import me.sora.eCommerce.service.ProductService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.FAILED;
import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ProductController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthenticationFilter.class}))
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenRequest_whenGetProductById_thenReturnProduct() throws Exception {
        // Given
        var product = new Product();
        var now = Instant.now();
        product.setId("id");
        product.setName("name");
        product.setDescription("description");
        product.setPrice(100.00);
        product.setCreatedDate(now);
        product.setUpdatedDate(now);
        var expectedResponse = ProductMapper.INSTANCE.fromProductEntityToGetProductResponse(product);

        // When
        when(productService.getProductById(anyString())).thenReturn(expectedResponse);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/products/{id}", product.getId())
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
                        .jsonPath("$.result.price")
                        .value(expectedResponse.getPrice()));
    }

    @Test
    void givenRequest_whenGetProductById_thenReturnDataNotFoundException() throws Exception {
        // Given

        // When
        when(productService.getProductById(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products/{id}", "not found id")
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
    @WithMockUser(authorities = {"ADMIN"})
    void givenRequest_whenCreateProduct_thenReturnSuccess() throws Exception {
        // Given
        var request = CreateProductRequest.builder()
                .name("name")
                .description("description")
                .amount(50)
                .price(100.00)
                .build();

        var now = Instant.now();
        var response = CreateProductResponse.builder()
                .id("id")
                .createdDate(now)
                .build();

        // When
        when(productService.createProduct(any(CreateProductRequest.class), anyString())).thenReturn(response);

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(SUCCESS))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.id")
                        .value(response.getId()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result.createdDate")
                        .value(now.toString()));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void givenRequest_whenCreateProduct_thenPermissionDenied() throws Exception {
        // Given
        var request = CreateProductRequest.builder()
                .name("name")
                .description("description")
                .amount(50)
                .price(100.00)
                .build();

        // When
        when(productService
                .createProduct(any(CreateProductRequest.class), anyString()))
                .thenThrow(new CustomException(ErrorConstant.PERMISSION_NOT_ALLOW, HttpStatus.UNAUTHORIZED));

        // Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.success")
                        .value(FAILED))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.result")
                        .value(ErrorConstant.PERMISSION_NOT_ALLOW));
    }

}

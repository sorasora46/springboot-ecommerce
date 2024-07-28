package me.sora.eCommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sora.eCommerce.config.AuthenticationFilter;
import me.sora.eCommerce.controller.ProductController;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;
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

}

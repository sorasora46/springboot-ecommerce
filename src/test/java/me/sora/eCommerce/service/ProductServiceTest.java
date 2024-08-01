package me.sora.eCommerce.service;

import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Product.CreateProductRequest;
import me.sora.eCommerce.dto.Product.CreateProductResponse;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.entity.Product;
import me.sora.eCommerce.entity.User;
import me.sora.eCommerce.repository.ProductRepository;
import me.sora.eCommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void givenRequest_whenGetProductById_thenReturnProduct() {
        // Given
        var now = Instant.now();
        var product = new Product();
        product.setId("id");
        product.setName("name");
        product.setDescription("description");
        product.setStockQuantity(1000);
        product.setPrice(1000.0);
        product.setUpdatedDate(now);
        product.setCreatedDate(now);
        var expectedResponse  = GetProductResponse.builder()
                .id("id")
                .name("name")
                .description("description")
                .price(1000.0)
                .updatedDate(now)
                .createdDate(now)
                .build();

        // When
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        // Then
        var response = productService.getProductById("id");
        assertNotNull(response);
        assertEquals(response.getId(), expectedResponse.getId());
        assertEquals(response.getName(), expectedResponse.getName());
        assertEquals(response.getDescription(), expectedResponse.getDescription());
        assertEquals(response.getPrice(), expectedResponse.getPrice());
        assertEquals(response.getUpdatedDate(), expectedResponse.getUpdatedDate());
        assertEquals(response.getCreatedDate(), expectedResponse.getCreatedDate());
    }

    @Test
    void givenRequest_whenGetProductById_thenDataNotFound() {
        // Given

        // When
        when(productRepository.findById(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        var exception = assertThrows(CustomException.class, () -> productService.getProductById("id"));
        assertNotNull(exception);
        assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
        assertEquals(exception.getMessage(), ErrorConstant.DATA_NOT_FOUND);
    }

    @Test
    void givenRequest_whenCreateProduct_thenSuccess() {
        // Given
        var now = Instant.now();

        var request = CreateProductRequest.builder()
                .name("name")
                .description("description")
                .stockQuantity(1000)
                .price(100.0)
                .build();

        var user = new User();
        user.setId("userId");
        user.setUsername("username");

        var product = new Product();
        product.setId("productId");
        product.setCreatedBy(user);
        product.setCreatedDate(now);
        product.setUpdatedBy(user);
        product.setUpdatedDate(now);

        var expectedResponse = CreateProductResponse.builder()
                .id("productId")
                .createdDate(now)
                .build();

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Then
        var response = productService.createProduct(request, "username");
        assertNotNull(response);
        assertEquals(response.getId(), expectedResponse.getId());
        assertEquals(response.getCreatedDate(), expectedResponse.getCreatedDate());
    }

    @Test
    void givenRequest_whenCreateProduct_thenDataNotFound() {
        // Given
        var request = CreateProductRequest.builder()
                .name("name")
                .description("description")
                .stockQuantity(1000)
                .price(100.0)
                .build();

        // When
        when(userRepository.findByUsername(anyString()))
                .thenThrow(new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Then
        var exception = assertThrows(CustomException.class, () -> productService.createProduct(request, "username"));
        assertNotNull(exception);
        assertEquals(exception.getMessage(), ErrorConstant.DATA_NOT_FOUND);
        assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void givenRequest_whenCreateProduct_thenDataDuplicate() {
        // Given
        var now = Instant.now();

        var request = CreateProductRequest.builder()
                .name("name")
                .description("description")
                .stockQuantity(1000)
                .price(100.0)
                .build();

        var user = new User();
        user.setId("userId");
        user.setUsername("username");

        var product = new Product();
        product.setId("productId");
        product.setCreatedBy(user);
        product.setCreatedDate(now);
        product.setUpdatedBy(user);
        product.setUpdatedDate(now);

        var sqlException = new DataAccessException("Duplicate entry") {
            @Override
            public Throwable getMostSpecificCause() {
                return new SQLException("Duplicate entry", "23000", 1062);
            }
        };

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(productRepository.save(any(Product.class))).thenThrow(sqlException);

        // Then
        var exception = assertThrows(DataAccessException.class, () -> productService.createProduct(request, "username"));
        var cause = ((SQLException) exception.getMostSpecificCause());
        assertNotNull(exception);
        assertNotNull(cause);
        assertEquals(cause.getErrorCode(), 1062);
        assertEquals(cause.getSQLState(), "23000");
    }

}

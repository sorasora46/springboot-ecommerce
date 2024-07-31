package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Product.CreateProductRequest;
import me.sora.eCommerce.dto.Product.CreateProductResponse;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.mapper.ProductMapper;
import me.sora.eCommerce.repository.ProductRepository;
import me.sora.eCommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public GetProductResponse getProductById(String id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));
        return ProductMapper.INSTANCE.fromProductEntityToGetProductResponse(product);
    }

    public CreateProductResponse createProduct(CreateProductRequest request, String username) {
        var createdBy = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));
        var newProduct = ProductMapper.INSTANCE.fromCreateProductRequestToProductEntity(request, createdBy);
        var savedProduct = productRepository.save(newProduct);
        return ProductMapper.INSTANCE.fromProductEntityToCreateProductResponse(savedProduct);
    }

}

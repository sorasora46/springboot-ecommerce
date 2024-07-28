package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.mapper.ProductMapper;
import me.sora.eCommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public GetProductResponse getProductById(String id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));
        return ProductMapper.INSTANCE.fromProductEntityToGetProductResponse(product);
    }

}

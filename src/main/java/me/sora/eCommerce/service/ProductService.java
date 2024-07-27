package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.mapper.ProductMapper;
import me.sora.eCommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public GetProductResponse getProductById(String id) {
        var product = productRepository.findById(id).orElse(null);
        return ProductMapper.INSTANCE.fromProductEntityToGetProductResponse(product);
    }

}

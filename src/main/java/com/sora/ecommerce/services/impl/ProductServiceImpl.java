package com.sora.ecommerce.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sora.ecommerce.exceptions.ApiException;
import com.sora.ecommerce.models.domains.Product;
import com.sora.ecommerce.models.requests.CreateProductPayload;
import com.sora.ecommerce.models.requests.UpdateProductPayload;
import com.sora.ecommerce.repositories.ProductRepository;
import com.sora.ecommerce.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Integer createProduct(CreateProductPayload payload) {
        List<String> images = payload.getImages();
        return null;
    }

    @Override
    public void deleteProductById(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("Product id cannot be null");

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Product id: " + id + " not found");

        productRepository.deleteById(id);
    }

    @Override
    public Product findProductById(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("Product id cannot be null");

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Product id: " + id + " not found");

        return optional.get();
    }

    @Override
    public List<Product> findProductByName(String name) {
        var result = productRepository.findByName(name);
        return result.get();
    }

    @Override
    public List<Product> findProductByPrice(Float min, Float max) {
        var result = productRepository.findByPrice(min, max);
        return result.get();
    }

    @Override
    public void updateProductById(Integer id, UpdateProductPayload payload) {
        if (id == null)
            throw new IllegalArgumentException("Product id cannot be null");

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Product id: " + id + " not found");

    }
}

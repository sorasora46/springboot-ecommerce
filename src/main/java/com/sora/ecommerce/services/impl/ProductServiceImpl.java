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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteProductById(Integer id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Product id: " + id + " not found");

        productRepository.deleteById(id);
    }

    @Override
    public Product findProductById(Integer id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Product id: " + id + " not found");

        return optional.get();
    }

    @Override
    public List<Product> findProductByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> findProductByPrice(Float min, Float max) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateProductById(Integer id, UpdateProductPayload payload) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Product id: " + id + " not found");

    }
}

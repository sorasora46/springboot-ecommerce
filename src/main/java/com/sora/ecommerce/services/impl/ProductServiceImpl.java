package com.sora.ecommerce.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${image.storage.path}")
    private String imageStoragePath;

    @Override
    public Integer createProduct(CreateProductPayload payload, MultipartFile[] images) {
        try {
            var newProduct = new Product(payload);
            var savedProduct = productRepository.save(newProduct);
            Integer productId = savedProduct.getId();

            String imagePaths = "";

            for (int i = 0; i < images.length; i++) {
                String fileName = productId + "-" + i + "-" + images[i].getOriginalFilename();
                imagePaths += fileName + ":";
                Path destination = Paths.get(imageStoragePath, fileName);
                Files.copy(images[i].getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            }

            savedProduct.setImagePaths(imagePaths);
            productRepository.save(savedProduct);

            return productId;
        } catch (Exception e) {
            throw new ApiException(e.getLocalizedMessage());
        }
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

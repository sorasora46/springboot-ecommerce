package com.sora.ecommerce.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sora.ecommerce.models.domains.Product;
import com.sora.ecommerce.models.requests.CreateProductPayload;
import com.sora.ecommerce.models.requests.UpdateProductPayload;

public interface ProductService {

    public Product findProductById(Integer id);

    public List<Product> findProductByName(String name);

    public List<Product> findProductByPrice(Float min, Float max);

    public Integer createProduct(CreateProductPayload payload, MultipartFile[] images);

    public void deleteProductById(Integer id);

    public void updateProductById(Integer id, UpdateProductPayload payload);

}

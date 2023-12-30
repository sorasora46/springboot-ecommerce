package com.sora.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sora.ecommerce.models.domains.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

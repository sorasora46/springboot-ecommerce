package com.sora.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sora.ecommerce.models.domains.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM products WHERE name LIKE '%?1%'", nativeQuery = true)
    public Optional<List<Product>> findByName(String name);

    @Query(value = "SELECT * FROM products WHERE price >= ?1 AND price <= ?2", nativeQuery = true)
    public Optional<List<Product>> findByPrice(Float min, Float max);

}

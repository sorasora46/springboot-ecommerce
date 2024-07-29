package me.sora.eCommerce.dto.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductRequest {
    private String name;
    private String description;
    private double price;
}

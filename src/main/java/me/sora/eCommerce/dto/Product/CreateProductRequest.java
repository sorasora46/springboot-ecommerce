package me.sora.eCommerce.dto.Product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductRequest {

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private int stockQuantity;

    @NotNull
    private double price;

}

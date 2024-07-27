package me.sora.eCommerce.dto.Product;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class GetProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private Instant createdDate;
    private Instant updatedDate;
}

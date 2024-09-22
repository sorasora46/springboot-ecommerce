package me.sora.eCommerce.dto.Product;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CreateProductResponse {
    private String id;
    private Instant createdDate;
}

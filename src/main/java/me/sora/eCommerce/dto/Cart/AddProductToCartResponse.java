package me.sora.eCommerce.dto.Cart;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AddProductToCartResponse {
    private String productId;
    private int quantity;
    private Instant addedDate;
}

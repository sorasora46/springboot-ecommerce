package me.sora.eCommerce.dto.Cart;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RemoveProductFromCartResponse {
    private String productId;
    private Instant deletedDate;
}

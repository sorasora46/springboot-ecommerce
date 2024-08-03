package me.sora.eCommerce.dto.Cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductToCartRequest {
    private String productId;
    private String quantity;
}

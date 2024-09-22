package me.sora.eCommerce.dto.Cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductToCartRequest {
    @NotEmpty
    private String productId;
    @NotNull
    private Integer quantity;
}

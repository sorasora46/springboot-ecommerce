package me.sora.eCommerce.dto.Cart;

import lombok.Builder;
import lombok.Data;
import me.sora.eCommerce.dto.Product.GetProductResponse;

import java.util.List;

@Data
@Builder
public class GetCartResponse {
    private List<GetProductResponse> cartItems;
}

package me.sora.eCommerce.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class CartItemId implements Serializable {

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "product_id")
    private String productId;

}

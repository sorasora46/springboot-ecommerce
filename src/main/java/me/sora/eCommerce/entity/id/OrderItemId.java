package me.sora.eCommerce.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class OrderItemId {

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_id")
    private String productId;

}

package me.sora.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.sora.eCommerce.entity.id.OrderItemId;

import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "added_date", nullable = false, updatable = false)
    private Instant addedDate;

    @PrePersist
    protected void onCreate() {
        addedDate = Instant.now();
    }

}

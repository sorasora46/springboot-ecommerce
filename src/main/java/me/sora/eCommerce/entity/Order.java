package me.sora.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "street_address", nullable = false, updatable = false)
    private String streetAddress;

    @Column(name = "city", nullable = false, updatable = false)
    private String city;

    @Column(name = "country", nullable = false, updatable = false)
    private String country;

    @Column(name = "post_code", nullable = false, updatable = false)
    private String postCode;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        this.createdDate = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        throw new CustomException(ErrorConstant.CREATED_ORDER_CAN_NOT_BE_UPDATE);
    }

}

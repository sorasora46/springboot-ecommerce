package me.sora.eCommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne()
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private User createdBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @ManyToOne()
    @JoinColumn(name = "updated_by", referencedColumnName = "id", nullable = false)
    private User updatedBy;

    @Column(name = "updated_date", nullable = false)
    private Instant updatedDate;

    @PrePersist
    protected void onCreate() {
        var now = Instant.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = Instant.now();
    }

}

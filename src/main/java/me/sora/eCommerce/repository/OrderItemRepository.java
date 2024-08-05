package me.sora.eCommerce.repository;

import me.sora.eCommerce.entity.OrderItem;
import me.sora.eCommerce.entity.id.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}

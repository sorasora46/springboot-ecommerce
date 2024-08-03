package me.sora.eCommerce.repository;

import me.sora.eCommerce.entity.CartItem;
import me.sora.eCommerce.entity.id.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
}

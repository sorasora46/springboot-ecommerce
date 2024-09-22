package me.sora.eCommerce.repository;

import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.entity.Cart;
import me.sora.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Query("SELECT new me.sora.eCommerce.dto.Product.GetProductResponse(" +
            "p.id," +
            "p.name, " +
            "p.description, " +
            "p.price, " +
            "ci.quantity, " +
            "p.createdDate, " +
            "p.updatedDate) " +
            "FROM carts c, cart_items ci, products p " +
            "WHERE c.user = :user AND ci.cart = c AND ci.product = p")
    List<GetProductResponse> findCartItemsByUser(@Param("user") User user);

    Optional<Cart> findCartByUser(User user);

}

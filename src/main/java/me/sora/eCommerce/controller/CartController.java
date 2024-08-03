package me.sora.eCommerce.controller;

import me.sora.eCommerce.dto.Cart.GetCartResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    // TODO: implement me
    // 1. get all items in cart (no pagination)
    // 2. if cart don't exist return null 200
    @GetMapping()
    public GetCartResponse getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }

    // TODO: implement me
    // 1. create cart when no cart exist
    // 2. if cart exist add item to cart
    // both cases: check if stockQuantity of product is enough
    @PostMapping()
    public Object addToCart() {
        return null;
    }

    // TODO: implement me
    // 1. return 500 if remove item from non-existing cart
    // 2. remove item from cart using id
    @DeleteMapping()
    public Object removeFromCart() {
        return null;
    }

}

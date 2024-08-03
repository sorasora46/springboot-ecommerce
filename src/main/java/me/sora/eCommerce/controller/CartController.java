package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Cart.*;
import me.sora.eCommerce.dto.CommonResponse;
import me.sora.eCommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // TODO: implement me
    // 1. get all items in cart (no pagination)
    // 2. if cart don't exist return null 200
    @GetMapping()
    public ResponseEntity<CommonResponse<GetCartResponse>> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        var response = cartService.getCart(userDetails.getUsername());
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

    // TODO: implement me
    // 1. create cart when no cart exist
    // 2. if cart exist add item to cart
    // both cases: check if stockQuantity of product is enough
    @PostMapping()
    public ResponseEntity<CommonResponse<AddProductToCartResponse>> addToCart(@Valid @RequestBody AddProductToCartRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        var response = cartService.addToCart(request, userDetails.getUsername());
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

    // TODO: implement me
    // 1. return 500 if remove item from non-existing cart
    // 2. remove item from cart using id
    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponse<RemoveProductFromCartResponse>> removeFromCart(@NotNull @PathVariable String productId, @AuthenticationPrincipal UserDetails userDetails) {
        var response = cartService.removeFromCart(productId, userDetails.getUsername());
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

    // TODO: validate action (ADD, REMOVE)
    @PatchMapping("/{productId}")
    public ResponseEntity<CommonResponse<UpdateProductInCartResponse>> updateCart(@NotNull @PathVariable String productId, @NotEmpty @RequestParam String action, @AuthenticationPrincipal UserDetails userDetails) {
        var response = cartService.updateProductInCart(productId, action, userDetails.getUsername());
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

}

package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Cart.AddProductToCartRequest;
import me.sora.eCommerce.dto.Cart.AddProductToCartResponse;
import me.sora.eCommerce.dto.Cart.GetCartResponse;
import me.sora.eCommerce.dto.Cart.RemoveProductFromCartResponse;
import me.sora.eCommerce.entity.Cart;
import me.sora.eCommerce.entity.id.CartItemId;
import me.sora.eCommerce.mapper.CartMapper;
import me.sora.eCommerce.repository.CartItemRepository;
import me.sora.eCommerce.repository.CartRepository;
import me.sora.eCommerce.repository.ProductRepository;
import me.sora.eCommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public GetCartResponse getCart(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        var items = cartRepository.findCartItemsByUser(user).orElse(null);
        return GetCartResponse.builder()
                .cartItems(items)
                .build();
    }

    public AddProductToCartResponse addToCart(AddProductToCartRequest request, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        var product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (product.getStockQuantity() - request.getQuantity() < 0) {
            throw new CustomException(ErrorConstant.EXCEED_LIMIT_QUANTITY, HttpStatus.BAD_REQUEST);
        }

        var cartOptional = cartRepository.findCartByUser(user);

        var cart = new Cart();
        if (cartOptional.isEmpty()) {
            var mappedCart = CartMapper.INSTANCE.fromAddProductToCartRequestToCart(request, user);
            cart = cartRepository.save(mappedCart);
        } else {
            cart = cartOptional.get();
        }

        var cartItemId = CartItemId.builder()
                .cartId(cart.getId())
                .productId(product.getId())
                .build();

        var cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            throw new CustomException(ErrorConstant.PRODUCT_ALREADY_ADDED_TO_CART, HttpStatus.CONFLICT);
        }

        var cartItem = CartMapper.INSTANCE.fromAddProductToCartRequestToCartItem(cartItemId, cart, product, request);
        var savedCartItem = cartItemRepository.save(cartItem);
        cart.setUpdatedDate(Instant.now());
        cartRepository.save(cart);

        return AddProductToCartResponse.builder()
                .productId(savedCartItem.getProduct().getId())
                .quantity(savedCartItem.getQuantity())
                .addedDate(savedCartItem.getAddedDate())
                .build();
    }

    public RemoveProductFromCartResponse removeFromCart(String productId, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        var cart = cartRepository.findCartByUser(user)
                .orElseThrow(() -> new CustomException(ErrorConstant.CART_NOT_EXIST, HttpStatus.NOT_FOUND));

        var cartItemId = CartItemId.builder()
                .cartId(cart.getId())
                .productId(productId)
                .build();
        var item = cartItemRepository.findById(cartItemId);
        cartItemRepository.deleteById(cartItemId);

        var now = Instant.now();
        if (item.isPresent()) {
            cart.setUpdatedDate(now);
            cartRepository.save(cart);
        }

        return RemoveProductFromCartResponse.builder()
                .productId(productId)
                .deletedDate(now)
                .build();
    }

}

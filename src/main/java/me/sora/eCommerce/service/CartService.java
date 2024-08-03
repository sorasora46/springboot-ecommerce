package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.repository.CartRepository;
import me.sora.eCommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public List<GetProductResponse> getCart(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));
        return cartRepository.findByUser(user).orElse(null);
    }

    public Object addToCart() {
        return null;
    }

    public Object removeFromCart() {
        return null;
    }

}

package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class OrderController {

    @GetMapping("/{orderId}")
    public Object getOrderById(@NotEmpty @PathVariable String orderId) {
        return null;
    }

    @GetMapping()
    public Object getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }

    @PostMapping()
    public Object createOrder(@Valid @RequestBody CreateOrderRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }

}

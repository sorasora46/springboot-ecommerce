package me.sora.eCommerce.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    @GetMapping("/{checkoutId}")
    public Object getCheckoutById(@NotEmpty @PathVariable String checkoutId) {
        return null;
    }

    @GetMapping()
    public Object getCheckouts(@AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }

    @PostMapping()
    public Object createCheckout(@AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }

}

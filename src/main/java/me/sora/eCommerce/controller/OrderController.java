package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.CommonResponse;
import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import me.sora.eCommerce.dto.Order.CreateOrderResponse;
import me.sora.eCommerce.dto.Order.GetOrderByIdResponse;
import me.sora.eCommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<GetOrderByIdResponse>> getOrderById(@NotEmpty @PathVariable String orderId) {
        var response = orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

    @GetMapping()
    public Object getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<CommonResponse<CreateOrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        var response = orderService.createOrder(request, userDetails.getUsername());
        var orderId = response.getOrderId();
        return ResponseEntity
                .created(URI.create(orderId))
                .body(CommonResponse.of(SUCCESS, response));
    }

}

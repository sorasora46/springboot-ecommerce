package me.sora.eCommerce.dto.Order;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CreateOrderResponse {
    private String orderId;
    private Instant createdDate;
}

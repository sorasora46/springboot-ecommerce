package me.sora.eCommerce.dto.Order;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class GetOrderByIdResponse {
    private OrderData order;
    private List<OrderItemData> orderItems;

    @Data
    @Builder
    public static class OrderData {
        private String orderId;
        private String streetAddress;
        private String city;
        private String country;
        private String postCode;
        private Instant createdDate;
        private double totalPrice;
    }

    @Data
    @Builder
    public static class OrderItemData {
        private String productId;
        private String productName;
        private String productDescription;
        private double price;
        private int quantity;
        private Instant addedDate;
    }
}

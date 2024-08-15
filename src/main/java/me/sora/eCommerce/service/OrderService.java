package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ApiConstant;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import me.sora.eCommerce.dto.Order.CreateOrderResponse;
import me.sora.eCommerce.dto.Order.GetOrderByIdResponse;
import me.sora.eCommerce.entity.OrderItem;
import me.sora.eCommerce.entity.Product;
import me.sora.eCommerce.mapper.OrderMapper;
import me.sora.eCommerce.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public GetOrderByIdResponse getOrderById(String orderId) {
        var orderDetail = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        var orders = orderItemRepository.findAllByOrderId(orderId);

        var itemIds = orders.stream().map(item -> item.getProduct().getId()).toList();
        var products = productRepository.findAllById(itemIds);
        var totalPrice = orders.stream().map(OrderItem::getPrice).reduce(0.0, Double::sum);

        return OrderMapper.INSTANCE.fromOrderAndOrderItemAndProductEntitiesToGetOrderByIdResponse(orderDetail, totalPrice, products, orders);
    }

    public Object getOrders(String username) {
        return null;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        var items = cartRepository.findRawCartItemsByUser(user);
        if (items.isEmpty()) {
            throw new CustomException(ErrorConstant.CAN_NOT_CREATE_ORDER_CART_EMPTY);
        }

        var productIds = items.stream()
                .map(item -> item.getProduct().getId())
                .toList();
        var products = productRepository.findAllById(productIds);

        List<Map<String, Object>> productErrorList = new ArrayList<>();
        for (var item : items) {
            var notFoundProducts = products.stream()
                    .filter(p ->
                            p.getStockQuantity() - item.getQuantity() < 0
                                    || !(item.getId().getProductId().equals(p.getId())))
                    .toList();

            for (var product : notFoundProducts) {
                Map<String, Object> errorDetails = new HashMap<>();
                errorDetails.put("productId", product.getId());
                errorDetails.put("productName", product.getName());
                errorDetails.put("stockQuantity", product.getStockQuantity());
                errorDetails.put("message", "Product stock is insufficient or product does not match the cart item.");
                productErrorList.add(errorDetails);
            }
        }
        if (!productErrorList.isEmpty()) {
            throw new CustomException(productErrorList, HttpStatus.CONFLICT);
        }

        var order = OrderMapper.INSTANCE.fromCreateOrderRequestToOrderEntity(request, user, ApiConstant.OrderStatus.WAITING_FOR_PAYMENT);
        order = orderRepository.save(order);

        var orderItems = OrderMapper.INSTANCE.fromCartItemListToOrderItemList(items, order);
        orderItemRepository.saveAll(orderItems);

        return CreateOrderResponse.builder()
                .orderId(order.getId())
                .createdDate(order.getCreatedDate())
                .build();
    }

}

package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.ErrorConstant;
import me.sora.eCommerce.controller.advice.CustomException;
import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import me.sora.eCommerce.dto.Order.CreateOrderResponse;
import me.sora.eCommerce.dto.Order.GetOrderByIdResponse;
import me.sora.eCommerce.entity.OrderItem;
import me.sora.eCommerce.mapper.OrderMapper;
import me.sora.eCommerce.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public GetOrderByIdResponse getOrderById(String orderId) {
        var orderDatail = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorConstant.DATA_NOT_FOUND, HttpStatus.NOT_FOUND));

        var orders = orderItemRepository.findAllByOrderId(orderId);

        var itemIds = orders.stream().map(item -> item.getProduct().getId()).toList();
        var products = productRepository.findAllById(itemIds);
        var totalPrice = orders.stream().map(OrderItem::getPrice).reduce(0.0, Double::sum);

        var response = OrderMapper.INSTANCE.fromOrderAndOrderItemAndProductEntitiesToGetOrderByIdResponse(orderDatail, totalPrice, products);

        return response;
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
        // TODO: compare amount by product id
        // from 1. item in cart table
        var productIds = items.stream()
                .map(item -> item.getProduct().getId())
                .toList();
        // 2. stock quantity from product table
        var products = productRepository.findAllById(productIds);

        var order = OrderMapper.INSTANCE.fromCreateOrderRequestToOrderEntity(request, user);
        orderRepository.save(order);

        var orderItems = OrderMapper.INSTANCE.fromCartItemListToOrderItemList(items, order);

        orderItemRepository.saveAll(orderItems);

        return null;
    }

}

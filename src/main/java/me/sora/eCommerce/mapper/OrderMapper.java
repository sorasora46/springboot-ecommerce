package me.sora.eCommerce.mapper;

import me.sora.eCommerce.constant.ApiConstant;
import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import me.sora.eCommerce.dto.Order.GetOrderByIdResponse;
import me.sora.eCommerce.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "order", source = "order")
    OrderItem fromCartItemToOrderItem(CartItem cartItem, Order order);

    default List<OrderItem> fromCartItemListToOrderItemList(List<CartItem> cartItemList,Order order) {
        return cartItemList.stream()
                .map(cartItem -> fromCartItemToOrderItem(cartItem, order))
                .toList();
    }

    @Mapping(target = "user", source = "user")
    Order fromCreateOrderRequestToOrderEntity(CreateOrderRequest createOrderRequest, User user, ApiConstant.OrderStatus status);

    @Mapping(target = "order", expression = "java(fromOrderEntityToOrderData(order, totalPrice))")
    @Mapping(target = "orderItems", expression = "java(fromProductEntitiesToOrderItemDataList(products, orders))")
    GetOrderByIdResponse fromOrderAndOrderItemAndProductEntitiesToGetOrderByIdResponse(
            Order order,
            double totalPrice,
            List<Product> products,
            List<OrderItem> orders
    );

    @Mapping(target = "orderId", source = "order.id")
    GetOrderByIdResponse.OrderData fromOrderEntityToOrderData(Order order, double totalPrice);

    default List<GetOrderByIdResponse.OrderItemData> fromProductEntitiesToOrderItemDataList(List<Product> products, List<OrderItem> orders) {
        List<GetOrderByIdResponse.OrderItemData> orderItemDataList = new ArrayList<>();
        for (var order : orders) {
            var orderItemData = products.stream()
                    .filter(product -> product.getId().equals(order.getId().getProductId()))
                    .map(product -> fromProductEntityToOrderItemData(product, order.getQuantity(), order.getAddedDate()))
                    .toList()
                    .get(0);
            orderItemDataList.add(orderItemData);
        }

        return orderItemDataList;
    }

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productDescription", source = "product.description")
    GetOrderByIdResponse.OrderItemData fromProductEntityToOrderItemData(Product product, double quantity, Instant addedDate);

}

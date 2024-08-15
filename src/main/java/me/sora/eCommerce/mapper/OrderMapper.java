package me.sora.eCommerce.mapper;

import me.sora.eCommerce.constant.ApiConstant;
import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import me.sora.eCommerce.dto.Order.GetOrderByIdResponse;
import me.sora.eCommerce.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
    @Mapping(target = "orderItems", expression = "java(fromProductEntitiesToOrderItemDataList(products))")
    GetOrderByIdResponse fromOrderAndOrderItemAndProductEntitiesToGetOrderByIdResponse(
            Order order,
            double totalPrice,
            List<Product> products
    );

    @Mapping(target = "orderId", source = "order.id")
    GetOrderByIdResponse.OrderData fromOrderEntityToOrderData(Order order, double totalPrice);

    default List<GetOrderByIdResponse.OrderItemData> fromProductEntitiesToOrderItemDataList(List<Product> products) {
        return products.stream()
                .map(this::fromProductEntityToOrderItemData)
                .toList();
    }

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productDescription", source = "product.description")
    GetOrderByIdResponse.OrderItemData fromProductEntityToOrderItemData(Product product);

}

package me.sora.eCommerce.mapper;

import me.sora.eCommerce.dto.Order.CreateOrderRequest;
import me.sora.eCommerce.entity.CartItem;
import me.sora.eCommerce.entity.Order;
import me.sora.eCommerce.entity.OrderItem;
import me.sora.eCommerce.entity.User;
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
    Order fromCreateOrderRequestToOrderEntity(CreateOrderRequest createOrderRequest, User user);

}

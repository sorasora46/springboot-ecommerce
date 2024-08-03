package me.sora.eCommerce.mapper;

import me.sora.eCommerce.dto.Cart.AddProductToCartRequest;
import me.sora.eCommerce.entity.Cart;
import me.sora.eCommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart fromAddProductToCartRequestToCart(AddProductToCartRequest request, User user);

}

package me.sora.eCommerce.mapper;

import me.sora.eCommerce.dto.Cart.AddProductToCartRequest;
import me.sora.eCommerce.entity.Cart;
import me.sora.eCommerce.entity.CartItem;
import me.sora.eCommerce.entity.Product;
import me.sora.eCommerce.entity.User;
import me.sora.eCommerce.entity.id.CartItemId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "user", source = "user")
    Cart fromAddProductToCartRequestToCart(AddProductToCartRequest request, User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "product", source = "product")
    CartItem fromAddProductToCartRequestToCartItem(
            CartItemId id,
            Cart cart,
            Product product,
            AddProductToCartRequest request
    );

}

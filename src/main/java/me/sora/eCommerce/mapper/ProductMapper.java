package me.sora.eCommerce.mapper;

import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    GetProductResponse fromProductEntityToGetProductResponse(Product product);

}

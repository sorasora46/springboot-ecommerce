package me.sora.eCommerce.mapper;

import me.sora.eCommerce.dto.Product.CreateProductRequest;
import me.sora.eCommerce.dto.Product.CreateProductResponse;
import me.sora.eCommerce.dto.Product.GetProductResponse;
import me.sora.eCommerce.entity.Product;
import me.sora.eCommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    GetProductResponse fromProductEntityToGetProductResponse(Product product);

    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "createdBy")
    Product fromCreateProductRequestToProductEntity(CreateProductRequest request, User createdBy);

    CreateProductResponse fromProductEntityToCreateProductResponse(Product product);

}

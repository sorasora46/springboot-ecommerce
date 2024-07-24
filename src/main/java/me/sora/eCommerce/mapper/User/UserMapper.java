package me.sora.eCommerce.mapper.User;

import me.sora.eCommerce.dto.User.CreateUserRequest;
import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    GetUserResponse fromUserEntityToGetUserResponse(User user);

}

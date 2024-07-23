package me.sora.eCommerce.mapper.User;

import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    GetUserResponse fromUserEntityToGetUserResponse(User user);

}

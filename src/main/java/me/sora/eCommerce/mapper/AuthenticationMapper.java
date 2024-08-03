package me.sora.eCommerce.mapper;

import me.sora.eCommerce.dto.Authentication.RegisterRequest;
import me.sora.eCommerce.dto.Authentication.RegisterResponse;
import me.sora.eCommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthenticationMapper {
    AuthenticationMapper INSTANCE = Mappers.getMapper(AuthenticationMapper.class);

    User fromRegisterRequestToUser(RegisterRequest registerRequest);

    RegisterResponse fromEntityToRegisterResponse(String accessToken, String refreshToken, User user);

}

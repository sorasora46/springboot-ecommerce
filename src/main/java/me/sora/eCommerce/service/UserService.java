package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.mapper.UserMapper;
import me.sora.eCommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public GetUserResponse getUserById(String id) {
        var user = userRepository.findById(id).orElse(null);
        return UserMapper.INSTANCE.fromUserEntityToGetUserResponse(user);
    }

    public GetUserResponse getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElse(null);
        return UserMapper.INSTANCE.fromUserEntityToGetUserResponse(user);
    }

}

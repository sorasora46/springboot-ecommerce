package me.sora.eCommerce.services;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.entities.User;
import me.sora.eCommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}

package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.AuthConstant;
import me.sora.eCommerce.dto.User.CreateUserRequest;
import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.entity.Credential;
import me.sora.eCommerce.mapper.User.UserMapper;
import me.sora.eCommerce.repository.CredentialRepository;
import me.sora.eCommerce.repository.UserRepository;
import me.sora.eCommerce.util.AuthUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    public GetUserResponse getUserById(String id) {
        var user = userRepository.findById(id).orElse(null);
        return UserMapper.INSTANCE.fromUserEntityToGetUserResponse(user);
    }

    public GetUserResponse getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElse(null);
        return UserMapper.INSTANCE.fromUserEntityToGetUserResponse(user);
    }

    public GetUserResponse createUser(CreateUserRequest request) {
        var user = UserMapper.INSTANCE.fromCreateUserRequestToUser(request);
        var savedUser = userRepository.save(user);

        String salt = authUtils.generateSalt(AuthConstant.SALT_LENGTH);
        String password = request.getPassword();
        String hashedPassword = passwordEncoder.encode(password + salt);

        var credential = new Credential();
        credential.setPassword(hashedPassword);
        credential.setSalt(salt);
        credential.setUser(savedUser);
        credential.setUpdatedDate(savedUser.getCreatedDate());

        credentialRepository.save(credential);

        return UserMapper.INSTANCE.fromUserEntityToGetUserResponse(savedUser);
    }

}

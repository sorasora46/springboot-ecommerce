package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.constant.AuthConstant;
import me.sora.eCommerce.dto.Authentication.AuthenticationRequest;
import me.sora.eCommerce.dto.Authentication.AuthenticationResponse;
import me.sora.eCommerce.dto.Authentication.RegisterRequest;
import me.sora.eCommerce.dto.Authentication.RegisterResponse;
import me.sora.eCommerce.entity.Credential;
import me.sora.eCommerce.mapper.Authentication.AuthenticationMapper;
import me.sora.eCommerce.repository.CredentialRepository;
import me.sora.eCommerce.repository.UserRepository;
import me.sora.eCommerce.util.AuthUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final AuthUtils authUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var username = request.getUsername();
        var password = request.getPassword();

        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        var salt = credentialRepository.findSaltByUserId(user).orElseThrow(() -> new UsernameNotFoundException(username));
        var saltedPassword = password + salt;

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, saltedPassword));

        var token = authUtils.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public RegisterResponse register(RegisterRequest request) {
        var user = AuthenticationMapper.INSTANCE.fromRegisterRequestToUser(request);
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

        var token = authUtils.generateToken(savedUser);

        return AuthenticationMapper.INSTANCE.fromEntityToRegisterResponse(token, savedUser);
    }


}

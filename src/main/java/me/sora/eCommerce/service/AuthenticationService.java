package me.sora.eCommerce.service;

import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Authentication.AuthenticationRequest;
import me.sora.eCommerce.dto.Authentication.AuthenticationResponse;
import me.sora.eCommerce.repository.CredentialRepository;
import me.sora.eCommerce.repository.UserRepository;
import me.sora.eCommerce.util.AuthUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final AuthUtils authUtils;

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

}

package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Authentication.AuthenticationRequest;
import me.sora.eCommerce.dto.Authentication.AuthenticationResponse;
import me.sora.eCommerce.dto.Authentication.RegisterRequest;
import me.sora.eCommerce.dto.Authentication.RegisterResponse;
import me.sora.eCommerce.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        var response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        var response = authenticationService.register(request);
        return ResponseEntity.created(URI.create(response.getId())).body(response);
    }

}

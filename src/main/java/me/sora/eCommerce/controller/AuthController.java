package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Authentication.AuthenticationRequest;
import me.sora.eCommerce.dto.Authentication.AuthenticationResponse;
import me.sora.eCommerce.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/logout")
    public Object logout() {
        return null;
    }

}

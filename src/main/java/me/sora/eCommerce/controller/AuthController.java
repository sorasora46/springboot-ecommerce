package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.Authentication.*;
import me.sora.eCommerce.dto.CommonResponse;
import me.sora.eCommerce.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static me.sora.eCommerce.constant.ApiConstant.ApiStatus.SUCCESS;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest request) {
        var response = authenticationService.authenticate(request);
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        var response = authenticationService.register(request);
        var userId = response.getId();
        return ResponseEntity
                .created(URI.create(userId))
                .body(CommonResponse.of(SUCCESS, response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<CommonResponse<RefreshTokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        var response = authenticationService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok().body(CommonResponse.of(SUCCESS, response));
    }

}

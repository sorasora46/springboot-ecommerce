package me.sora.eCommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.User.CreateUserRequest;
import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<GetUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        var newUser = userService.createUser(request);
        return ResponseEntity.created(URI.create(newUser.getId())).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@NotEmpty @PathVariable String id) {
        var response = userService.getUserById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<GetUserResponse> getUserByUsername(@NotEmpty @PathVariable String username) {
        var response = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(response);
    }

}

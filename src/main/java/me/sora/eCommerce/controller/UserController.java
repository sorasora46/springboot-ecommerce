package me.sora.eCommerce.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.dto.User.GetUserResponse;
import me.sora.eCommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

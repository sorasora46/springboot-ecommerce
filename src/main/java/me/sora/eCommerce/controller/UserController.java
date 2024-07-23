package me.sora.eCommerce.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import me.sora.eCommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@NotEmpty @PathVariable String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Object> getUserByUsername(@NotEmpty @PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

}

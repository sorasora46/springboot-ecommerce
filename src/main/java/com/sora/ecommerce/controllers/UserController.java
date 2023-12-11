package com.sora.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sora.ecommerce.models.domains.User;
import com.sora.ecommerce.models.requests.CreateUserPayload;
import com.sora.ecommerce.services.UserService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return user;
    }

    @PostMapping("/create")
    public UUID createUser(@RequestBody CreateUserPayload payload) {
        UUID id = userService.createUser(payload);
        return id;
    }

}
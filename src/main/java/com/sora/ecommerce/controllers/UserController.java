package com.sora.ecommerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id) {
        return id;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody String info) {
        return info;
    }

}

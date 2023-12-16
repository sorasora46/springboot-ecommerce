package com.sora.ecommerce.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sora.ecommerce.exceptions.ApiException;
import com.sora.ecommerce.models.domains.User;
import com.sora.ecommerce.models.requests.CreateUserPayload;
import com.sora.ecommerce.repositories.UserRepository;
import com.sora.ecommerce.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "User id: " + id + " not found");

        return user.get();
    }

    @Override
    public UUID createUser(CreateUserPayload payload) {
        String firstName = payload.getFirstName();
        String lastName = payload.getLastName();
        String email = payload.getEmail();

        User newUser = new User(firstName, lastName, email);

        if (userRepository.existsByEmail(email))
            throw new ApiException(HttpStatus.CONFLICT, email + " already exist");
        if (userRepository.existsByName(firstName, lastName))
            throw new ApiException(HttpStatus.CONFLICT, firstName + " " + lastName + " already exist");

        User result = userRepository.save(newUser);

        return result.getId();
    }
}

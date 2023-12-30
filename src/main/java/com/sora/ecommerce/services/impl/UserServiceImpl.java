package com.sora.ecommerce.services.impl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sora.ecommerce.auth.UserCredential;
import com.sora.ecommerce.exceptions.ApiException;
import com.sora.ecommerce.models.domains.User;
import com.sora.ecommerce.models.requests.CreateUserPayload;
import com.sora.ecommerce.models.requests.UpdateUserPayload;
import com.sora.ecommerce.repositories.UserCredentialRepository;
import com.sora.ecommerce.repositories.UserRepository;
import com.sora.ecommerce.services.UserService;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

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
        String username = payload.getUsername();
        String password = payload.getPassword();

        User newUser = new User(firstName, lastName, email);

        String hashedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray());

        if (userRepository.existsByEmail(email))
            throw new ApiException(HttpStatus.CONFLICT, email + " already exist");
        if (userRepository.existsByName(firstName, lastName))
            throw new ApiException(HttpStatus.CONFLICT, firstName + " " + lastName + " already exist");
        if (userCredentialRepository.existsByUsername(username))
            throw new ApiException(HttpStatus.CONFLICT, username + " already exist");

        User result = userRepository.save(newUser);

        UserCredential newUserCredential = new UserCredential(result.getId(), username, hashedPassword);
        userCredentialRepository.save(newUserCredential);

        return result.getId();
    }

    @Override
    public void deleteUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "User id: " + id + " not found");

        userRepository.deleteById(id);
    }

    @Override
    public void updateUserById(UUID id, UpdateUserPayload payload) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "User id: " + id + " not found");

        var user = optional.get();
        if (payload.getFirstName() != null)
            user.setFirstName(payload.getFirstName());
        if (payload.getLastName() != null)
            user.setLastName(payload.getLastName());
        if (payload.getEmail() != null)
            user.setEmail(payload.getEmail());

        userRepository.save(user);
    }
}

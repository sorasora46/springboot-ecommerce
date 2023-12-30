package com.sora.ecommerce.services;

import java.util.UUID;

import com.sora.ecommerce.models.domains.User;
import com.sora.ecommerce.models.requests.CreateUserPayload;
import com.sora.ecommerce.models.requests.UpdateUserPayload;

public interface UserService {

    public User getUserById(UUID id);

    public UUID createUser(CreateUserPayload payload);

    public void deleteUserById(UUID id);

    public void updateUserById(UUID id, UpdateUserPayload payload);
}

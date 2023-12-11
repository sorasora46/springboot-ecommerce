package com.sora.ecommerce.services;

import java.util.UUID;

import com.sora.ecommerce.models.domains.User;
import com.sora.ecommerce.models.requests.CreateUserPayload;

public interface UserService {

    public User getUserById(UUID id);

    public UUID createUser(CreateUserPayload payload);
}

package com.haiphamcoder.demo.application.repository;

import java.util.List;
import java.util.Optional;

import com.haiphamcoder.demo.domain.entity.primary.User;

public interface UserRepository {
    List<User> getAllUsers();

    User createUser(User user);

    Optional<User> getUserByUsername(String username);

    User getUserById(Long id);
}

package com.haiphamcoder.demo.application.repository;

import java.util.List;

import com.haiphamcoder.demo.domain.entity.primary.User;

public interface UserRepository {
    List<User> getAllUsers();
}

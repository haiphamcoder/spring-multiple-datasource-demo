package com.haiphamcoder.demo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haiphamcoder.demo.application.repository.UserRepository;
import com.haiphamcoder.demo.domain.entity.primary.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}

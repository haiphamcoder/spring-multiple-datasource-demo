package com.haiphamcoder.demo.infrastructure.persistence.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haiphamcoder.demo.application.repository.UserRepository;
import com.haiphamcoder.demo.domain.entity.primary.User;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long>{
    @Override
    default List<User> getAllUsers() {
        return findAll();
    }

    @Override
    default User createUser(User user) {
        return save(user);
    }
}

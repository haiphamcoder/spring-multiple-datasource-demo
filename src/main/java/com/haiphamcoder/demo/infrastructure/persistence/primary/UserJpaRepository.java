package com.haiphamcoder.demo.infrastructure.persistence.primary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haiphamcoder.demo.application.repository.UserRepository;
import com.haiphamcoder.demo.domain.entity.primary.User;

@Repository
public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long>{
    
    Optional<User> findByUsername(String username);
    
    @Override
    default List<User> getAllUsers() {
        return findAll();
    }

    @Override
    default User createUser(User user) {
        return save(user);
    }

    @Override
    default Optional<User> getUserByUsername(String username) {
        return findByUsername(username);
    }

    @Override
    default User getUserById(Long id) {
        return findById(id).orElse(null);
    }
}

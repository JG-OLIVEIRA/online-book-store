package com.dev.jorge.onlinebookstore.adapters.outbound.repositories;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.UserJpaEntity;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;

    public UserRepositoryImpl(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity UserEntity = new UserJpaEntity(user);
        this.repository.save(UserEntity);
        return new User(UserEntity.getId(), UserEntity.getUsername(), UserEntity.getEmail(), UserEntity.getPassword());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserJpaEntity> userEntity = this.repository.findByUsername(username);
        return userEntity.map(entity -> new User(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword()));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email).map(entity -> new User(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword()));
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }
}

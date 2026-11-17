package com.dev.jorge.onlinebookstore.adapters.outbound.repositories.user;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.UserJpaEntity;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    public UserRepositoryImpl(UserJpaRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = new UserJpaEntity(user);
        return mapper.toDomain(this.repository.save(entity));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserJpaEntity> userEntity = this.repository.findByUsername(username);
        return userEntity.map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email).map(mapper::toDomain);
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

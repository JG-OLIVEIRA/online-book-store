package com.dev.jorge.onlinebookstore.adapters.outbound.repositories.user;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.UserJpaEntity;
import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.user.SignUpUserRequestDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "roles", source = "roles")
    })
    User toDomain(UserJpaEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "username", source = "username"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "roles", ignore = true)
    })
    User toDomain(SignUpUserRequestDTO request);

}

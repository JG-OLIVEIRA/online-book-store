package com.dev.jorge.onlinebookstore.utils.mappers;

import com.dev.jorge.onlinebookstore.domain.user.User;
import com.dev.jorge.onlinebookstore.domain.user.UserDataResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "entity.username", source = "username"),
        @Mapping(target = "entity.email", source = "email"),
        @Mapping(target = "entity.password", source = "password")
    })
    UserDataResponseDTO toDTO(User entity);
}

package com.dev.jorge.onlinebookstore.adapters.inbound.mapper;

import com.dev.jorge.onlinebookstore.adapters.inbound.dto.book.GetBookResponseDTO;

import com.dev.jorge.onlinebookstore.application.command.book.CreateBookResult;
import com.dev.jorge.onlinebookstore.application.command.book.GetBookByIdResult;

import com.dev.jorge.onlinebookstore.adapters.inbound.dto.book.CreateBookResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "author", source = "author"),
            @Mapping(target = "isbn", source = "isbn"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "price", source = "price")
    })
    CreateBookResponseDTO toCreateBookResponseDTO(CreateBookResult result);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "author", source = "author"),
            @Mapping(target = "isbn", source = "isbn"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "price", source = "price")
    })
    GetBookResponseDTO toGetBookResponseDTO(GetBookByIdResult result);

}

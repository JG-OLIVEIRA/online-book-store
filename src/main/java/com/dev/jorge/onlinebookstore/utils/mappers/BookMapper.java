package com.dev.jorge.onlinebookstore.utils.mappers;

import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.book.BookDataResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "book.id", source = "id"),
            @Mapping(target = "book.title", source = "title"),
            @Mapping(target = "book.author", source = "author"),
            @Mapping(target = "book.isbn", source = "isbn"),
            @Mapping(target = "book.stock", source = "stock"),
            @Mapping(target = "book.price", source = "price")
    })
    BookDataResponseDTO toDTO(Book book);
}

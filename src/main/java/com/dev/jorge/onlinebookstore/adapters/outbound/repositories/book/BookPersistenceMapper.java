package com.dev.jorge.onlinebookstore.adapters.outbound.repositories.book;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.BookJpaEntity;
import com.dev.jorge.onlinebookstore.domain.book.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookPersistenceMapper {
    Book toDomain(BookJpaEntity entity);
}

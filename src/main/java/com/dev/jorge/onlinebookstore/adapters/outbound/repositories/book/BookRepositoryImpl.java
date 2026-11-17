package com.dev.jorge.onlinebookstore.adapters.outbound.repositories.book;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.BookJpaEntity;
import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.book.BookRepository;
import com.dev.jorge.onlinebookstore.domain.common.PageResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository repository;
    private final BookPersistenceMapper mapper;

    public BookRepositoryImpl(BookJpaRepository repository, BookPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Book save(Book book) {
        BookJpaEntity entity = new BookJpaEntity(book);
        return mapper.toDomain(this.repository.save(entity));
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return this.repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public PageResult<Book> findAll(int page, int size) {
        Page<Book> result = this.repository.findAll(PageRequest.of(page, size)).map(mapper::toDomain);
        return new PageResult<>(result.getContent(), result.getNumber(), result.getSize(), result.getTotalElements());
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }

}

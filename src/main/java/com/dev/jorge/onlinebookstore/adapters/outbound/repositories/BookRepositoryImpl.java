package com.dev.jorge.onlinebookstore.adapters.outbound.repositories;

import com.dev.jorge.onlinebookstore.adapters.outbound.entities.BookJpaEntity;
import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.book.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository repository;

    public BookRepositoryImpl(BookJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        BookJpaEntity entity = new BookJpaEntity(book);
        this.repository.save(entity);
        return new Book(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn(), entity.getStock(), entity.getPrice());
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return this.repository.findById(id)
                .map(entity -> new Book(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn(), entity.getStock(), entity.getPrice()));
    }

    @Override
    public Page<Book> findAll(int page, int size) {
        return this.repository.findAll(PageRequest.of(page, size))
                .map(entity -> new Book(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn(), entity.getStock(), entity.getPrice()));
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }

    @Override
    public Page<Book> findAll(PageRequest pageRequest) {
        return this.repository.findAll(pageRequest)
                .map(entity -> new Book(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getIsbn(), entity.getStock(), entity.getPrice()));
    }
}

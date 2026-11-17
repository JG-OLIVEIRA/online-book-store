package com.dev.jorge.onlinebookstore.infrastructure.config;

import com.dev.jorge.onlinebookstore.application.ports.PasswordService;
import com.dev.jorge.onlinebookstore.application.ports.SessionTokenService;
import com.dev.jorge.onlinebookstore.application.service.AuthServiceImpl;
import com.dev.jorge.onlinebookstore.application.service.BookServiceImpl;
import com.dev.jorge.onlinebookstore.application.service.OrderServiceImpl;
import com.dev.jorge.onlinebookstore.application.usecases.AuthUseCases;
import com.dev.jorge.onlinebookstore.application.usecases.BookUseCases;
import com.dev.jorge.onlinebookstore.application.usecases.OrderUseCases;
import com.dev.jorge.onlinebookstore.domain.book.BookRepository;
import com.dev.jorge.onlinebookstore.domain.inventory.InventoryRepository;
import com.dev.jorge.onlinebookstore.domain.order.OrderRepository;
import com.dev.jorge.onlinebookstore.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
public class UseCaseConfiguration {

    @Bean
    AuthUseCases authUseCases(UserRepository repository, SessionTokenService tokens, PasswordService passwords) {
        return new ValidatedAuthService(repository, tokens, passwords);
    }

    @Bean
    BookUseCases bookUseCases(BookRepository repository) {
        return new ValidatedBookService(repository);
    }

    @Bean
    InventoryUseCases inventoryUseCases(InventoryRepository repository) {
        return new ValidatedInventoryService(repository);
    }

    @Bean
    OrderUseCases orderUseCases(OrderRepository repository) {
        return new ValidatedOrderService(repository);
    }

    @Validated
    static class ValidatedAuthService extends AuthServiceImpl {
        ValidatedAuthService(UserRepository repository, SessionTokenService tokens, PasswordService passwords) {
            super(repository, tokens, passwords);
        }
    }

    @Validated
    static class ValidatedBookService extends BookServiceImpl {
        ValidatedBookService(BookRepository repository) {
            super(repository);
        }
    }

    @Validated
    static class ValidatedInventoryService extends InventoryServiceImpl {
        ValidatedInventoryService(InventoryRepository repository) {
            super(repository);
        }
    }

    @Validated
    static class ValidatedOrderService extends OrderServiceImpl {
        ValidatedOrderService(OrderRepository repository) {
            super(repository);
        }
    }
}

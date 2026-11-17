package com.dev.jorge.onlinebookstore.domain;

import com.dev.jorge.onlinebookstore.domain.book.Book;
import com.dev.jorge.onlinebookstore.domain.inventory.Inventory;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.inventory.CreateInventoryResponseDTO;
import com.dev.jorge.onlinebookstore.domain.order.Order;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.order.CreateOrderResponseDTO;
import com.dev.jorge.onlinebookstore.domain.user.Role;
import com.dev.jorge.onlinebookstore.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DomainModelsTests {
    @Test
    @DisplayName("Livros comparam identidade e preservam atributos")
    void booksCompareByIdentity() {
        UUID id = UUID.randomUUID();
        Book book = new Book(id, "Title", "Author", "ISBN", BigDecimal.TEN);
        Book equal = new Book(id, "Other", "Writer", "Other", 2, BigDecimal.ONE);
        Book different = new Book(UUID.randomUUID(), "Title", "Author", "ISBN", BigDecimal.TEN);
        assertEquals(book, book);
        assertNotEquals(book, null);
        assertNotEquals(book, "book");
        assertEquals(book, equal);
        assertNotEquals(book, different);
        assertEquals(book.hashCode(), equal.hashCode());
        assertEquals(0, book.getStock());
    }

    @Test
    @DisplayName("Usuarios comparam identidade e preservam dados informados")
    void usersCompareByIdentity() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "name", "First", "Last", "email", "password");
        User equal = new User(id, "other", "F", "L", "other", "other");
        User different = new User(UUID.randomUUID(), "name", "F", "L", "email", "password");
        User withoutId = new User("name", "First", "Last", "email", "password");
        assertEquals(user, user);
        assertNotEquals(user, null);
        assertNotEquals(user, "user");
        assertEquals(user, equal);
        assertNotEquals(user, different);
        assertEquals(user.hashCode(), equal.hashCode());
        assertNull(withoutId.getId());
        assertEquals("First", withoutId.getFirstName());
        assertTrue(withoutId.getRoles().contains(Role.ROLE_USER));
    }

    @Test
    @DisplayName("Modelos de estoque e pedido mantem os dados")
    void inventoryAndOrderPreserveValues() {
        UUID id = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setBookId(bookId);
        inventory.setQuantity(5);
        InventoryJpaEntity entity = new InventoryJpaEntity(inventory);
        assertEquals(id, entity.getId());
        assertEquals(bookId, entity.getBookId());
        assertEquals(5, entity.getQuantity());
        Order order = new Order(id, bookId, BigDecimal.TEN, 2);
        assertEquals(id, order.getId());
        assertEquals(bookId, order.getBookId());
        assertEquals(BigDecimal.TEN, order.getPrice());
        assertEquals(2, order.getQuantity());
        assertEquals(id, new CreateInventoryResponseDTO(id).id());
        assertEquals(bookId, new InventoryDataResponseDTO(id, bookId, 5, true).bookId());
        assertEquals(id, new UserDataResponseDTO(id, "user", "mail").id());
        assertNotNull(new CreateOrderResponseDTO());
        assertNotNull(new OrderDataResponseDTO());
    }
}

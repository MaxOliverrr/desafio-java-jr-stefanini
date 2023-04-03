package com.maxoliver.desafiojavajrstefanini.respositories;

import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import com.maxoliver.desafiojavajrstefanini.util.BookFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Tests for repository layer")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("findFirstByNameContains must returns a book by name when successful")
    void findFirstByNameContains_ReturnsABook_WhenSuccessful() {
        Book savedBook = bookRepository.save(BookFactory.bookNoId());
        Optional<Book> bookOptionalToTest = bookRepository.findFirstByNameContains(savedBook.getName());
        assertThat(bookOptionalToTest).isNotNull().isNotEmpty();
        assertThat(savedBook).isEqualTo(bookOptionalToTest.get());
    }
}
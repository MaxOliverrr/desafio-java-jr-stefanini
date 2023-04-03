package com.maxoliver.desafiojavajrstefanini.services;

import com.maxoliver.desafiojavajrstefanini.exceptions.NotFoundException;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import com.maxoliver.desafiojavajrstefanini.respositories.BookRepository;
import com.maxoliver.desafiojavajrstefanini.util.BookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Unit tests for service layer")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        when(bookRepository.findAll()).thenReturn(List.of(BookFactory.bookWithId()));
        when(bookRepository.save(any(Book.class))).thenReturn(BookFactory.bookWithId());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(BookFactory.bookWithId()));
        when(bookRepository.findFirstByNameContains(anyString())).thenReturn(Optional.ofNullable(BookFactory.bookWithId()));
        doNothing().when(bookRepository).delete(any(Book.class));
        when(bookRepository.existsById(anyLong())).thenReturn(true);
    }

    @Test
    @DisplayName("findAll must returns a list of books when successful")
    void findAll_ReturnsAListOfBooks_WhenSuccessful() {
        List<Book> findAll = bookService.findAll();
        var expectedBook = BookFactory.bookWithId();
        assertThat(findAll).isNotNull().isNotEmpty();
        assertThat(findAll.get(0)).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("findAll must returns a empty list of books when successful")
    void findAll_ReturnsAEmptyListOfBooks_WhenSuccessful(){
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        List<Book> findAll = bookService.findAll();
        assertThat(findAll).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save must saves a book and when successful")
    void save_MustSaveAndReturnsABook_WhenSuccessful() {
        var expectedBook = BookFactory.bookWithId();
        var bookToTest = bookService.save(BookFactory.bookNoId());
        assertThat(bookToTest).isNotNull()
            .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("findById must returns a book when successful")
    void findById_ReturnsABook_WhenSuccessful() {
        var expectedBook = BookFactory.bookWithId();
        var bookToTest = bookService.findById(expectedBook.getId());
        assertThat(bookToTest.getId()).isNotNull()
            .isEqualTo(expectedBook.getId());
    }

    @Test
    @DisplayName("findById must returns a NotFoundException when successful")
    void findById_ThrowsANotFoundException_WhenSuccessful(){
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> bookService.findById(1L));
        assertThat(notFoundException.getMessage()).isEqualTo("Obra não encontrada");
    }

    @Test
    @DisplayName("findByName must returns a book when successful")
    void findByName_ReturnsABook_WhenSuccessful() {
        var expectedBook = BookFactory.bookWithId();
        var bookToTest = bookService.findByName("");
        assertThat(bookToTest).isNotNull();
        assertThat(bookToTest.getName()).isEqualTo(expectedBook.getName());
    }

    @Test
    @DisplayName("findByName must throws a NotFoundException when successful")
    void findByName_ThrowsANotFoundException_WhenSuccessful(){
        when(bookRepository.findFirstByNameContains(anyString())).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> bookService.findByName(""));
        assertThat(notFoundException.getMessage()).isEqualTo("Obra não encontrada");
    }

    @Test
    @DisplayName("delete must delete a book when successful")
    void delete_MustDeleteABook_WhenSuccessful() {
        bookService.delete(1L);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    @DisplayName("existsBookById must returns a boolean if a book exists or not when successful")
    void existsBookById_MustReturnABoolean_WhenSuccessful() {
        bookService.existsBookById(1L);
        verify(bookRepository, times(1)).existsById(1L);
    }
}
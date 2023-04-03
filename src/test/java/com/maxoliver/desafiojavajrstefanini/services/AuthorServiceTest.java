package com.maxoliver.desafiojavajrstefanini.services;

import com.maxoliver.desafiojavajrstefanini.exceptions.EntityInUseException;
import com.maxoliver.desafiojavajrstefanini.exceptions.NotFoundException;
import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import com.maxoliver.desafiojavajrstefanini.respositories.AuthorRepository;
import com.maxoliver.desafiojavajrstefanini.util.AuthorFactory;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Unit tests for service layer")
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp(){
        when(authorRepository.findAll()).thenReturn(List.of(AuthorFactory.authorWithId()));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(AuthorFactory.authorWithId()));
        when(authorRepository.save(any())).thenReturn(AuthorFactory.authorWithId());
        when(authorRepository.findFirstByNameContains(anyString())).thenReturn(Optional.of(AuthorFactory.authorWithId()));
        doNothing().when(authorRepository).delete(any(Author.class));
        when(authorRepository.existsById(anyLong())).thenReturn(true);
    }

    @Test
    @DisplayName("findAll return list of authors when successful")
    void findAll_ReturnsAListOfAuthors_WhenSuccessful() {
        List<Author> authors = authorService.findAll();
        var expectedAuthor = AuthorFactory.authorWithId();
        assertThat(authors).isNotEmpty().isNotNull();
        assertThat(authors.get(0)).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("findAll return empty list of authors when successful")
    void findAll_ReturnsAEmptyListOfAuthors_WhenSuccessful() {
        when(authorRepository.findAll()).thenReturn(Collections.emptyList());
        List<Author> authors = authorService.findAll();
        assertThat(authors).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save a new author and returns it when successful")
    void save_MustSaveAndReturnsAAuthor_WhenSuccessful() {
        var authorToTest = authorService.save(null);
        var expectedAuthor = AuthorFactory.authorWithId();
        assertThat(authorToTest).isNotNull()
            .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("findById returns author when successful")
    void findById_ReturnsAAuthor_WhenSuccessful() {
        var authorToTest = authorService.findById(1L);
        var expectedAuthor = AuthorFactory.authorWithId();
        assertThat(authorToTest.getId()).isNotNull()
            .isEqualTo(expectedAuthor.getId());
    }

    @Test
    @DisplayName("findById throws a NotFoundException when author not found")
    void findById_ThrowsNotFoundException_WhenAuthorNotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> authorService.findById(1L));
        assertThat(notFoundException.getMessage()).isEqualTo("Autor não encontrado");
    }

    @Test
    @DisplayName("findByName returns author when successful")
    void findByName_ReturnsAAuthor_WhenSuccessful() {
        var authorToTest = authorService.findByName("");
        var expectedAuthor = AuthorFactory.authorWithId();
        assertThat(authorToTest).isNotNull();
        assertThat(authorToTest.getName()).isEqualTo(expectedAuthor.getName());
    }

    @Test
    @DisplayName("findByName throws a NotFoundException when author not found")
    void findByName_ThrowsANotFoundException_WhenAuthorNotFound() {
        when(authorRepository.findFirstByNameContains(anyString())).thenReturn(Optional.empty());
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> authorService.findByName(""));
        assertThat(notFoundException.getMessage()).isEqualTo("Autor não encontrado");
    }

    @Test
    @DisplayName("delete author when successful")
    void delete_MustDeleteAnAuthor_WhenSuccessful() {
        authorService.delete(1L);
        verify(authorRepository, times(1)).delete(any(Author.class));
    }

    @Test
    @DisplayName("delete throws a entity in use exception when exists an association with a book")
    void delete_ThrowsAEntityInUseException_WhenExistsAnAssociationWithABook(){
        var expectedAuthor = authorRepository.findById(1L).get();
        expectedAuthor.getBookList().add(BookFactory.bookWithId());
        EntityInUseException entityInUseException = assertThrows(EntityInUseException.class, () -> authorService.delete(1L));
        assertThat(entityInUseException.getMessage()).isEqualTo("O autor está associado a uma ou mais obras e não pôde ser excluído");
    }

    @Test
    @DisplayName("existsAuthorById must returns a boolean if a author exists or not when successful")
    void existById_existsAuthorById_MustReturnABoolean_WhenSuccessful(){
        authorService.exitsAuthorById(1L);
        verify(authorRepository, times(1)).existsById(1L);
    }
}
package com.maxoliver.desafiojavajrstefanini.controller;

import com.maxoliver.desafiojavajrstefanini.dtos.ResponseAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import com.maxoliver.desafiojavajrstefanini.services.AuthorService;
import com.maxoliver.desafiojavajrstefanini.services.BookService;
import com.maxoliver.desafiojavajrstefanini.util.AuthorFactory;
import com.maxoliver.desafiojavajrstefanini.util.BookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for controller layer")
class AuthorControllerTest {
    @Spy
    private ModelMapper modelMapper;

    @Mock
    private AuthorService authorService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        when(authorService.findAll()).thenReturn(List.of(AuthorFactory.authorWithId()));
        when(authorService.findById(anyLong())).thenReturn(AuthorFactory.authorWithId());
        when(authorService.findByName(anyString())).thenReturn(AuthorFactory.authorWithId());
        when(authorService.save(any())).thenReturn(AuthorFactory.authorWithId());
        when(bookService.findById(anyLong())).thenReturn(BookFactory.bookWithId());
        doNothing().when(authorService).delete(anyLong());

    }

    @Test
    @DisplayName("findAll returns an list of ResponseAuthorDTO when Successful")
    void findAll_ReturnsAnListOfResponseAuthorDTO_WhenSuccessful() {
        var expectedAuthor = AuthorFactory.responseAuthorDTO();
        List<ResponseAuthorDTO> authorListToTest = authorController.findAll().getBody();
        assertThat(authorListToTest).isNotNull().isNotEmpty().hasSize(1);
        assertThat(authorListToTest.get(0)).isEqualTo(expectedAuthor);
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("findById must returns a ResponseAuthorDTO when Successful")
    void findById_ReturnsAnResponseAuthorDTO_WhenSuccessful() {
        var expectedAuthor = AuthorFactory.responseAuthorDTO();
        var authorToTest = authorController.findById(1L).getBody();
        assertThat(authorToTest).isNotNull().isEqualTo(expectedAuthor);
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("findByName must returns a ResponseAuthorDTO when Successful")
    void findByName_ReturnsAnResponseAuthorDTO_WhenSuccessful() {
        var expectedAuthor = AuthorFactory.responseAuthorDTO();
        var authorToTest = authorController.findByName("").getBody();
        assertThat(authorToTest).isNotNull().isEqualTo(expectedAuthor);
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("save must persists author returns a ResponseAuthorDTO when successful")
    void save_PersistsAnAuthor_WhenSuccessful() {
        var expectedAuthor = AuthorFactory.responseAuthorDTO();
        var authorToTest = authorController.save(AuthorFactory.requestAuthorDTO()).getBody();
        assertThat(authorToTest).isNotNull().isEqualTo(expectedAuthor);
        verify(modelMapper, times(2)).map(any(), any());
    }

    @Test
    @DisplayName("update must calls service methods, save and existsAuthorById when successful")
    void update_CallsServiceMethods_WhenSuccessful() {
        authorController.update(1L, AuthorFactory.requestAuthorDTO());
        verify(authorService, times(1)).save(any());
        verify(authorService, times(1)).exitsAuthorById(anyLong());
        verify(modelMapper, times(2)).map(any(), any());
    }

    @Test
    @DisplayName("registerBooks must associate a book in author adding in list and persist when successful")
    void registerBooks_AssociateAndPersistAnBookInAuthor_WhenSuccessful(){
        Book expectedBook = BookFactory.bookWithId();
        var authorToTest = authorController.registerBooks(1L, BookFactory.registerBookDTO()).getBody();
        assertThat(authorToTest).isNotNull();
        assertThat(authorToTest.getBookList()).contains(expectedBook);
        verify(authorService, times(1)).findById(anyLong());
        verify(bookService, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("delete must calls the delete method in service layer one time when successful")
    void delete_CallsTheDeleteMethod_WhenSuccessful(){
        authorController.delete(1L);
        verify(authorService, times(1)).delete(1L);
    }
}
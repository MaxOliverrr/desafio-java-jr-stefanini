package com.maxoliver.desafiojavajrstefanini.controller;

import com.maxoliver.desafiojavajrstefanini.dtos.ResponseBookDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BooksControllerTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BooksController booksController;

    @BeforeEach
    void setUp() {
        when(bookService.findAll()).thenReturn(List.of(BookFactory.bookWithId()));
        when(bookService.findById(anyLong())).thenReturn(BookFactory.bookWithId());
        when(bookService.findByName(anyString())).thenReturn(BookFactory.bookWithId());
        when(bookService.save(any())).thenReturn(BookFactory.bookWithId());
        when(authorService.findById(anyLong())).thenReturn(AuthorFactory.authorWithId());
        doNothing().when(bookService).delete(anyLong());
    }

    @Test
    @DisplayName("findAll must returns a list of ResponseBookDTO when Successful")
    void findAll_ReturnsAnListOfResponseBookDTO() {
        var expectedBook = BookFactory.responseBookDTO();
        List<ResponseBookDTO> bookListToTest = booksController.findAll().getBody();
        assertThat(bookListToTest).isNotNull().isNotEmpty().hasSize(1);
        assertThat(bookListToTest.get(0)).isEqualTo(expectedBook);
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("findById must returns a ResponseBookDTO when Successful")
    void findById_ReturnsAnResponseBookDTO_WhenSuccessful() {
        var expectedBook = BookFactory.responseBookDTO();
        var bookToTest = booksController.findById(1L).getBody();
        assertThat(bookToTest).isNotNull().isEqualTo(expectedBook);
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("findByName must returns a ResponseBookDTO when Successful")
    void findByName_ReturnsAnResponseBookDTO_WhenSuccessful() {
        var expectedBook = BookFactory.responseBookDTO();
        var bookToTest = booksController.findByName("").getBody();
        assertThat(bookToTest).isNotNull().isEqualTo(expectedBook);
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("save must persists book returns a ResponseBookDTO when successful")
    void save_PersistsAnBook_WhenSuccessful() {
        var expectedBook = BookFactory.responseBookDTO();
        var bookToTest = booksController.save(BookFactory.requestBookDTO()).getBody();
        assertThat(bookToTest).isEqualTo(expectedBook);
        verify(modelMapper, times(2)).map(any(), any());
    }

    @Test
    @DisplayName("update must calls service methods, save and existsBookById when successful")
    void update_CallsServiceMethods_WhenSuccessful() {
        booksController.update(1L, BookFactory.requestBookDTO());
        verify(bookService, times(1)).save(any());
        verify(bookService, times(1)).existsBookById(anyLong());
        verify(modelMapper, times(2)).map(any(), any());
    }

    @Test
    @DisplayName("registerAuthors must associate a author in book adding in list and persist when successful")
    void registerAuthors_AssociateAndPersistAnAuthorInBook_WhenSuccessful() {
        var expectedAuthor = AuthorFactory.authorWithId();
        var bookToTest = booksController.registerAuthors(1L, AuthorFactory.registerAuthorDTO()).getBody();
        assertThat(bookToTest).isNotNull();
        assertThat(bookToTest.getAuthorList()).contains(expectedAuthor);
        verify(authorService, times(1)).findById(anyLong());
        verify(bookService, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    @DisplayName("delete must calls the delete method in service layer one time when successful")
    void delete_CallsTheDeleteMethod_WhenSuccessful() {
        booksController.delete(1L);
        verify(bookService, times(1)).delete(anyLong());
    }
}
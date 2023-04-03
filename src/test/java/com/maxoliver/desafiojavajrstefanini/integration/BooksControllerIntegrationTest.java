package com.maxoliver.desafiojavajrstefanini.integration;

import com.maxoliver.desafiojavajrstefanini.dtos.RequestBookDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.ResponseBookDTO;
import com.maxoliver.desafiojavajrstefanini.respositories.BookRepository;
import com.maxoliver.desafiojavajrstefanini.util.BookFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class BooksControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.saveAll(List.of(BookFactory.bookNoId(), BookFactory.book2NoId()));
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("findAll returns an list of BookDTO when Successful")
    void findAll_ReturnsAnListOfBookDTO_WhenSuccessful() {
        ResponseEntity<ResponseBookDTO[]> response = testRestTemplate
                .getForEntity("/stefannini/library/books", ResponseBookDTO[].class);

        assertThat(response.getBody()).isNotNull().isNotEmpty().hasSize(2);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("findById must returns a BookDTO when Successful")
    void findById_ReturnsBookDTO_WhenSuccessful() {
        ResponseEntity<ResponseBookDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/books/book/{id}", ResponseBookDTO.class, 15);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(15L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("findById must returns a Not Found status when Successful")
    void findById_ReturnsANotFound_WhenSuccessful() {
        ResponseEntity<ResponseBookDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/books/book/{id}", ResponseBookDTO.class, 5);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    @DisplayName("findByName must returns a BookDTO when Successful")
    void FindByName_ReturnsBookDTO_WhenSuccessful() {
        var expectedBook = BookFactory.responseBookDTO();
        ResponseEntity<ResponseBookDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/books/book?name={name}", ResponseBookDTO.class, "The Lord");

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(expectedBook.getName());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("findByName must returns a Not Found status when Successful")
    void FindByName_ReturnsANotFound_WhenSuccessful() {
        ResponseEntity<ResponseBookDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/books/book?name={name}", ResponseBookDTO.class, "123");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    @DisplayName("save must persists Book returns a BookDTO when Successful")
    void save_PersistsAndSaveBookDTO_WhenSuccessful() {
        var requestBookDTO = RequestBookDTO
                .builder()
                .name("Beren and Lúthien")
                .description("Beren and Lúthien is a compilation of multiple versions of the epic fantasy Lúthien and Beren by J. R. R. Tolkien, one of Tolkien's earliest tales of Middle-earth.")
                .publishDate(LocalDate.of(2017,6,1))
                .build();

        ResponseEntity<ResponseBookDTO> response = testRestTemplate
                .postForEntity("/stefannini/library/books", requestBookDTO, ResponseBookDTO.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
    }

    @Test
    @DisplayName("save must returns a Bad Request status when Successful")
    void save_MustReturnsBadRequest_WhenSuccessful() {
        var postBookDTO = BookFactory.requestBookDTO();
        ResponseEntity<ResponseBookDTO> response = testRestTemplate
                .postForEntity("/stefannini/library/books", postBookDTO, ResponseBookDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    @DisplayName("update must update a property of Book and returns a BookDTO when Successful")
    void update_UpdateAnBook_WhenSuccessful() {
        testRestTemplate.put("/stefannini/library/books/book/{id}", BookFactory.bookDTOUpdated(), 19);
        ResponseEntity<ResponseBookDTO> response = testRestTemplate.getForEntity("/stefannini/library/books/book/{id}", ResponseBookDTO.class, 19);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(19L);
        assertThat(response.getBody().getName()).isEqualTo("The Lord of the Rings 2");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("delete must delete a Book")
    void delete_DeleteAnBook_WhenSuccessful(){
        testRestTemplate.delete("/stefannini/library/books/book/{id}", 1);
        ResponseEntity<ResponseBookDTO> response = testRestTemplate.getForEntity("/stefannini/library/books/book/{id}", ResponseBookDTO.class, 1);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }
}
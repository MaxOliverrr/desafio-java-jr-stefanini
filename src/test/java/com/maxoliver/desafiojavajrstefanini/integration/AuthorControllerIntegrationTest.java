package com.maxoliver.desafiojavajrstefanini.integration;

import com.maxoliver.desafiojavajrstefanini.dtos.RequestAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.dtos.ResponseAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Country;
import com.maxoliver.desafiojavajrstefanini.respositories.AuthorRepository;
import com.maxoliver.desafiojavajrstefanini.util.AuthorFactory;
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

class AuthorControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository.saveAll(List.of(AuthorFactory.authorNoId(), AuthorFactory.author2NoId()));
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    @DisplayName("findAll returns an list of AuthorDTO when Successful")
    void findAll_ReturnsAnListOfAuthorDTO_WhenSuccessful() {
        ResponseEntity<ResponseAuthorDTO[]> response = testRestTemplate
                .getForEntity("/stefannini/library/authors", ResponseAuthorDTO[].class);

        assertThat(response.getBody()).isNotNull().isNotEmpty().hasSize(2);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("findById must returns a AuthorDTO when Successful")
    void findById_ReturnsAuthorDTO_WhenSuccessful() {
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/authors/author/{id}", ResponseAuthorDTO.class, 20);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(20L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("findById must returns a Not Found status when Successful")
    void findById_ReturnsANotFound_WhenSuccessful() {
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/authors/author/{id}", ResponseAuthorDTO.class, 5);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    @DisplayName("findByName must returns a AuthorDTO when Successful")
    void FindByName_ReturnsAuthorDTO_WhenSuccessful() {
        var expectedAuthor = AuthorFactory.responseAuthorDTO();
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/authors/author?name={name}", ResponseAuthorDTO.class, "Tolkien");

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(expectedAuthor.getName());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("findByName must returns a Not Found status when Successful")
    void FindByName_ReturnsANotFound_WhenSuccessful() {
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate
                .getForEntity("/stefannini/library/authors/author?name={name}", ResponseAuthorDTO.class, "123");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    @DisplayName("save must persists Author returns a AuthorDTO when Successful")
    void save_PersistsAndSaveAuthorDTO_WhenSuccessful() {
        var requestAuthorDTO = RequestAuthorDTO
                .builder()
                .name("Machado de Assis")
                .email("machado@mail.com")
                .cpf("961.575.290-83")
                .birthDate(LocalDate.now())
                .country(Country.builder().name("Brasil").build())
                .build();

        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate
                .postForEntity("/stefannini/library/authors", requestAuthorDTO, ResponseAuthorDTO.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
    }

    @Test
    @DisplayName("save must returns a Bad Request status when Successful")
    void save_MustReturnsBadRequest_WhenSuccessful() {
        var postAuthorDTO = AuthorFactory.requestAuthorDTO();
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate
                .postForEntity("/stefannini/library/authors", postAuthorDTO, ResponseAuthorDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    @DisplayName("update must update a property of Author and returns a AuthorDTO when Successful")
    void update_UpdateAnAuthor_WhenSuccessful() {
        testRestTemplate.put("/stefannini/library/authors/author/{id}", AuthorFactory.authorDTO2Updated(), 11);
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate.getForEntity("/stefannini/library/authors/author/{id}", ResponseAuthorDTO.class, 11);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(11L);
        assertThat(response.getBody().getName()).isEqualTo("Chaya Pinkhasivna Lispector");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    @DisplayName("delete must delete a Author when successful")
    void delete_DeleteAnAuthor_WhenSuccessful(){
        testRestTemplate.delete("/stefannini/library/authors/author/{id}", 5);
        ResponseEntity<ResponseAuthorDTO> response = testRestTemplate.getForEntity("/stefannini/library/authors/author/{id}", ResponseAuthorDTO.class, 5);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }
}
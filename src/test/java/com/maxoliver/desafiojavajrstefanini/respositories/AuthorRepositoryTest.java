package com.maxoliver.desafiojavajrstefanini.respositories;

import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import com.maxoliver.desafiojavajrstefanini.util.AuthorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Tests for repository layer")
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("findFirstByNameContains must returns Author by name")
    void findFirstByNameContains_ReturnsAuthor_WhenSuccessful() {
        Author savedAuthor = authorRepository.save(AuthorFactory.authorNoId());
        Optional<Author> authorOptionalToTest = authorRepository.findFirstByNameContains(savedAuthor.getName());
        assertThat(authorOptionalToTest).isNotNull().isNotEmpty();
        assertThat(savedAuthor).isEqualTo(authorOptionalToTest.get());
    }
}
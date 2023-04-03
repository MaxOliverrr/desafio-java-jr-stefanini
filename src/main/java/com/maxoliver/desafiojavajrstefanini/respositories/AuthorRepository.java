package com.maxoliver.desafiojavajrstefanini.respositories;

import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findFirstByNameContains(String name);

}

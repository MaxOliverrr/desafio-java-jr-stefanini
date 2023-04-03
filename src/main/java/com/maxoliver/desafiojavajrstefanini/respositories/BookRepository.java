package com.maxoliver.desafiojavajrstefanini.respositories;

import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findFirstByNameContains(String name);
}

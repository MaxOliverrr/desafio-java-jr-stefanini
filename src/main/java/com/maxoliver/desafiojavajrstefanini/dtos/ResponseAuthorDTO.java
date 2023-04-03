package com.maxoliver.desafiojavajrstefanini.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import com.maxoliver.desafiojavajrstefanini.model.entities.Country;
import com.maxoliver.desafiojavajrstefanini.model.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseAuthorDTO {

    private Long id;
    private String name;
    private Gender gender;
    private String email;
    private LocalDate birthDate;
    private Country country;
    private String cpf;
    private Set<Book> bookList = new HashSet<>();
}

package com.maxoliver.desafiojavajrstefanini.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseBookDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDate publishDate;
    private LocalDate exposureDate;
    private Set<Author> authorList;
}

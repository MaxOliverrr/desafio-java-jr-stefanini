package com.maxoliver.desafiojavajrstefanini.dtos;

import com.maxoliver.desafiojavajrstefanini.model.entities.Author;
import com.maxoliver.desafiojavajrstefanini.validator.ValidadeDates;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@ValidadeDates
public class RequestBookDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatorio")
    private String name;

    @NotBlank(message = "A descrição é é obrigatoria")
    private String description;

    private LocalDate publishDate;

    private LocalDate exposureDate;

    private Set<Author> authorList;
}

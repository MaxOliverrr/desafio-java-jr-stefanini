package com.maxoliver.desafiojavajrstefanini.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maxoliver.desafiojavajrstefanini.model.entities.Book;
import com.maxoliver.desafiojavajrstefanini.model.entities.Country;
import com.maxoliver.desafiojavajrstefanini.model.entities.enums.Gender;
import com.maxoliver.desafiojavajrstefanini.validator.CPF_Required;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@CPF_Required
@AllArgsConstructor
public class RequestAuthorDTO {

    private Long id;
    @NotBlank(message = "O nome é obrigatório")

    private String name;

    private Gender gender;

    @Email(message = "Digite um email válido")
    private String email;

    @PastOrPresent(message = "A data deve ser no presente ou no passado")
    private LocalDate birthDate;

    private Country country;

    @CPF(message = "Digite um cpf válido")
    private String cpf;

    private Set<Book> bookList;

}

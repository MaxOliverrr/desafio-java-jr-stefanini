package com.maxoliver.desafiojavajrstefanini.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegisterAuthorDTO {

    @NotBlank(message = "O id é obrigatório")
    private Long id;
}

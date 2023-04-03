package com.maxoliver.desafiojavajrstefanini.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country{
    
    @JsonProperty("nativeName")
    @Column(name = "country_name", nullable = false)
    @NotBlank(message = "O nome é obrigatório")
    private String name;

}

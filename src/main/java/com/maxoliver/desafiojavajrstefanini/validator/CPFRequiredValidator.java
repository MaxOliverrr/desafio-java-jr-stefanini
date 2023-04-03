package com.maxoliver.desafiojavajrstefanini.validator;

import com.maxoliver.desafiojavajrstefanini.dtos.RequestAuthorDTO;
import com.maxoliver.desafiojavajrstefanini.model.entities.Country;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class CPFRequiredValidator implements ConstraintValidator<CPF_Required, RequestAuthorDTO> {

    @Override
    public boolean isValid(RequestAuthorDTO author, ConstraintValidatorContext constraintValidatorContext) {
                String path = "https://restcountries.com/v2/name/" + author.getCountry().getName();
                Country country = Objects.requireNonNull(new RestTemplate().getForEntity(path, Country[].class).getBody())[0];
                author.setCountry(country);

        if(author.getCountry().getName().equalsIgnoreCase("Brasil") && author.getCpf() == null){
            return false;
        }

        if(!author.getCountry().getName().equalsIgnoreCase("Brasil")){
            author.setCpf(null);
        }

        return true;
    }
}

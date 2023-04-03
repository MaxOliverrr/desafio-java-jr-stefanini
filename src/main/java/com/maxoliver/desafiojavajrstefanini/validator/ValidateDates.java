package com.maxoliver.desafiojavajrstefanini.validator;

import com.maxoliver.desafiojavajrstefanini.dtos.RequestBookDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateDates implements ConstraintValidator<ValidadeDates, RequestBookDTO> {
    @Override
    public boolean isValid(RequestBookDTO bookDTO, ConstraintValidatorContext constraintValidatorContext) {
        if(bookDTO.getExposureDate() == null && bookDTO.getPublishDate() == null){
            return false;
        }
        return true;
    }
}

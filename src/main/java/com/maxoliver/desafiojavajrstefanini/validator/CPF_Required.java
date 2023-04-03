package com.maxoliver.desafiojavajrstefanini.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CPFRequiredValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface CPF_Required {
    String message() default "O cpf deve ser informado";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}


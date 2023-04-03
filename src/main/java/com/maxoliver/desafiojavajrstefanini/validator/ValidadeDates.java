package com.maxoliver.desafiojavajrstefanini.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {ValidateDates.class}
)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidadeDates {
    String message() default "Informe a data de publicação ou a data de exposição";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}

package com.citronix.citronix.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SaisonValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSaison {
    String message() default "La saison doit être une valeur valide : HIVER, PRINTEMPS, ETE, ou AUTOMME.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

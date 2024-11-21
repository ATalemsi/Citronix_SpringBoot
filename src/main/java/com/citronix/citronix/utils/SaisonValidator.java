package com.citronix.citronix.utils;
import com.citronix.citronix.entity.Enum.Saison;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SaisonValidator implements ConstraintValidator<ValidSaison, Saison> {

    @Override
    public boolean isValid(Saison value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // @NotNull will handle null check
        }

        // Validate that the value belongs to the Enum Saison
        return value == Saison.HIVER || value == Saison.PRINTEMPS ||
                value == Saison.ETE || value == Saison.AUTOMME;
    }
}

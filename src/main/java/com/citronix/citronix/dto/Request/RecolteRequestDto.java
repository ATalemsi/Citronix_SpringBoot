package com.citronix.citronix.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class RecolteRequestDto {

    @NotBlank(message = "La saison est obligatoire.")
    @Pattern(regexp = "^(hiver|printemps|été|automne)$", message = "La saison doit être hiver, printemps, été, ou automne.")
    private String saison;

    @NotNull(message = "La date de récolte est obligatoire.")
    @PastOrPresent(message = "La date de récolte doit être dans le passé ou le présent.")
    private LocalDate dateRecolte;
}

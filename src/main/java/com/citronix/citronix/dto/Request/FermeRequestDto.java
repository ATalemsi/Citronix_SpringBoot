package com.citronix.citronix.dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeRequestDto {

    @NotBlank(message = "Le nom de la ferme est obligatoire.")
    @Size(max = 200, message = "Le nom ne doit pas dépasser 200 caractères.")
    private String nom;

    @NotBlank(message = "La localisation est obligatoire.")
    @Size(max = 200, message = "La localisation ne doit pas dépasser 200 caractères.")
    private String localisation;

    @NotNull(message = "La superficie est obligatoire.")
    @Positive(message = "La superficie doit être supérieure à 0.")
    private Double superficie;

    @NotNull(message = "La date de création est obligatoire.")
    @PastOrPresent(message = "La date de création doit être dans le passé ou le présent.")
    private LocalDate dateCreation;
}

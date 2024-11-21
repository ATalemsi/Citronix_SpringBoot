package com.citronix.citronix.dto.Request;

import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.utils.ValidSaison;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecolteRequestDto {

    @NotNull(message = "La saison est obligatoire.")
    @ValidSaison
    private Saison saison;

    @NotNull(message = "La date de récolte est obligatoire.")
    @PastOrPresent(message = "La date de récolte doit être dans le passé ou le présent.")
    private LocalDate dateRecolte;


    @NotNull(message = "Champ ID is required.")
    private Long champId;
}

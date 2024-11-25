package com.citronix.citronix.dto.Request;

import com.citronix.citronix.dto.CreateGroup;
import com.citronix.citronix.dto.UpdateGroup;
import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.utils.ValidSaison;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RecolteRequestDto {

    @NotNull(groups = CreateGroup.class, message = "La saison est obligatoire.")
    @ValidSaison
    private Saison saison;

    @NotNull(groups = CreateGroup.class, message = "La date de récolte est obligatoire.")
    @PastOrPresent(message = "La date de récolte doit être dans le passé ou le présent.")
    private LocalDate dateRecolte;

    @NotNull(groups = CreateGroup.class, message = "Champ ID is required.")
    private Long champId;
}

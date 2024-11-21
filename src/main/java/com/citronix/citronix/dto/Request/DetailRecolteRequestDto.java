package com.citronix.citronix.dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DetailRecolteRequestDto {
    @NotNull(message = "La quantité récoltée est obligatoire.")
    @Positive(message = "La quantité doit être un nombre positif.")
    private Double quantite;

    @NotNull(message = "L'ID de l'arbre est obligatoire.")
    private Long arbreId;

    @NotNull(message = "L'ID de la récolte est obligatoire.")
    private Long recolteId;
}

package com.citronix.citronix.dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChampRequestDto
{
    @NotNull(message = "La superficie du champ est obligatoire.")
    @Positive(message = "La superficie doit être un nombre positif.")
    private Double superficie;

    @NotNull(message = "L'ID de la ferme est obligatoire.")
    private Long fermeId;
}

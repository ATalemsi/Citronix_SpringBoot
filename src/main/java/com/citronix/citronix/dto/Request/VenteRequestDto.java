package com.citronix.citronix.dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VenteRequestDto {
    @NotNull(message = "La date de vente est obligatoire.")
    @PastOrPresent(message = "La date de vente doit être dans le passé ou le présent.")
    private LocalDate dateVente;

    @NotNull(message = "Le prix unitaire est obligatoire.")
    @Positive(message = "Le prix unitaire doit être un nombre positif.")
    private Double prixUnitaire;

    @NotNull(message = "La quantité vendue est obligatoire.")
    @Positive(message = "La quantité vendue doit être un nombre positif.")
    private Double quantiteVendue;

    @NotNull(message = "Le nom du client est obligatoire.")
    private String clientName;

    @NotNull(message = "L'email du client est obligatoire.")
    private String clientEmail;

    @NotNull(message = "L'ID de la récolte est obligatoire.")
    private Long recolteId;
}

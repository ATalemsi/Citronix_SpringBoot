package com.citronix.citronix.dto.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VenteResponseDto {
    private Long id;
    private LocalDate dateVente;
    private Double quantiteVendue;
    private Double prixUnitaire;
    private Double revenu;

    private Long recolteId;
    private String recolteSaison;

    private String clientName;
    private String clientEmail;
}

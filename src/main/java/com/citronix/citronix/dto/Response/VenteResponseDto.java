package com.citronix.citronix.dto.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VenteResponseDto {
    private Long idVente;
    private LocalDate dateVente;
    private Double prixUnitaire;
    private Double quantiteVendue;
    private Double revenu;
    private Long recolteId;
}

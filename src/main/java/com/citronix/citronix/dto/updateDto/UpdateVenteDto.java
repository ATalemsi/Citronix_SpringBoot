package com.citronix.citronix.dto.updateDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateVenteDto {
    private Long id;
    private LocalDate dateVente;
    private Double quantiteVendue;
    private Double prixUnitaire;
    private Long recolteId;
    private String clientName;
    private String clientEmail;
}

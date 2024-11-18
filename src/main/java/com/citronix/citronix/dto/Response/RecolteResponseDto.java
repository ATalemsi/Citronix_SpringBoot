package com.citronix.citronix.dto.Response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RecolteResponseDto {
    private Long idRecolte;
    private String saison;
    private LocalDate dateRecolte;
    private Double quantiteTotale;
    private List<DetailRecolteResponseDto> detailsRecolte;
}

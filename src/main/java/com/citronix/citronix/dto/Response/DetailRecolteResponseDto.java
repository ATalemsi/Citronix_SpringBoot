package com.citronix.citronix.dto.Response;

import lombok.Data;

@Data
public class DetailRecolteResponseDto {
    private Long id;
    private Double quantite;
    private Long arbreId;
    private Long recolteId;
}

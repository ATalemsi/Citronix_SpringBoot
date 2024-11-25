package com.citronix.citronix.dto.Response;

import lombok.Data;

@Data
public class DetailRecolteResponseDto {
    private Long id;
    private Double quantite;
    private ArbreResponseDto arbre;
    private Long recolteId;
}

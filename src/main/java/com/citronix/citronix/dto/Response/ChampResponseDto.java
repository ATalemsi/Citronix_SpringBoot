package com.citronix.citronix.dto.Response;

import com.citronix.citronix.entity.Ferme;
import lombok.Data;

@Data
public class ChampResponseDto {
    private Long id;
    private Double superficie;
    private String fermeNom;
    private String localisation;
}

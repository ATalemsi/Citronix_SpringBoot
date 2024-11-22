package com.citronix.citronix.dto.Response;

import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RecolteResponseDto {
    private Long id;
    private Saison saison;
    private LocalDate dateRecolte;
    private Double quantiteTotale;
    private ChampResponseDto champ;
    private List<DetailRecolteResponseDto> detailsRecolte;


}

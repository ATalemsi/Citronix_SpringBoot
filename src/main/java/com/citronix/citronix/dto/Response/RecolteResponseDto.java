package com.citronix.citronix.dto.Response;

import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RecolteResponseDto {
    private Long id;
    private Saison saison;
    private LocalDate dateRecolte;
    private Double quantiteTotale;
    private List<DetailRecolteResponseDto> detailsRecolte;


}

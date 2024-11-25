package com.citronix.citronix.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeResponseDto {
    private Long id;
    private String nom;
    private String localisation;
    private Double superficie;
    private LocalDate dateCreation;
    private List<ChampResponseDto> champs;
}

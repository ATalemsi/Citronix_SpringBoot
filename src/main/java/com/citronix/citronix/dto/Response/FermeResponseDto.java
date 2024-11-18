package com.citronix.citronix.dto.Response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FermeResponseDto {
    private Long idFerme;
    private String nom;
    private String localisation;
    private Double superficie;
    private LocalDate dateCreation;
    private List<ChampResponseDto> champs;
}

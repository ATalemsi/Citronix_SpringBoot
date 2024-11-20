package com.citronix.citronix.dto.Response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArbreResponseDto {
    private Long id;
    private LocalDate datePlantation;
    private int age;
    private String productivite;
    private ChampResponseDto champResponseDto;
}

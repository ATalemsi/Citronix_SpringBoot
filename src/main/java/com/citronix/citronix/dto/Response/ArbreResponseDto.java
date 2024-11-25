package com.citronix.citronix.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ArbreResponseDto {
    private Long id;
    private LocalDate datePlantation;
    private int age;
    private String productivite;
    private ChampResponseDto champResponseDto;
}

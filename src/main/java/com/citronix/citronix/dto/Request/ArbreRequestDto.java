package com.citronix.citronix.dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ArbreRequestDto {

    @NotNull(message = "La date de plantation est obligatoire.")
    @PastOrPresent(message = "La date de plantation doit être dans le passé ou le présent.")
    private LocalDate datePlantation;

    @NotNull(message = "L'ID du champ est obligatoire.")
    private Long champId;

}

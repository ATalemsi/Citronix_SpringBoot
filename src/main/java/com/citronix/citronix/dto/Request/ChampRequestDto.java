package com.citronix.citronix.dto.Request;

import com.citronix.citronix.dto.UpdateGroup;
import com.citronix.citronix.entity.Ferme;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChampRequestDto
{
    @NotNull(message = "La superficie est requise.")
    @DecimalMin(value = "0.1", message = "La superficie minimale est de 0.1 hectare.")
    @NotNull(groups = UpdateGroup.class,message = "La superficie est requise.")
    @DecimalMin(groups = UpdateGroup.class,value = "0.1", message = "La superficie minimale est de 0.1 hectare.")
    private Double superficie;

    @NotNull(message = "L'ID de la ferme est obligatoire.")
    @Null(groups = UpdateGroup.class)
    private Long fermeId;
}

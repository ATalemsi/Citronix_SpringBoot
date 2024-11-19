package com.citronix.citronix.dto.search;

import lombok.Data;

import java.time.LocalDate;
@Data
public class FermeSearchCriteria {

    private String nom;
    private String localisation;
    private Double minSuperficie;
    private Double maxSuperficie;
    private LocalDate startDate;
    private LocalDate endDate;
}

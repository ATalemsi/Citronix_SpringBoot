package com.citronix.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate dateVente;

    @NotNull
    private Double prixUnitaire;

    @NotNull
    private Double quantiteVendue;

    @ManyToOne
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;
}

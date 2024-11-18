package com.citronix.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recoltedetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private Double quantite;

    @ManyToOne
    @JoinColumn(name = "arbre_id")
    private Arbre arbre;


    @ManyToOne
    @JoinColumn(name = "recolte_id")
    private Recolte recolte;
}

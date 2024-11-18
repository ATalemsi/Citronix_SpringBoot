package com.citronix.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table (name = "ferme")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ferme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String localisation;


    @Positive
    private double superficier;


    @PastOrPresent
    private LocalDate dateCreation;


    @OneToMany(mappedBy = "ferme" ,cascade = CascadeType.ALL ,orphanRemoval = true)
    private Set<Champ> champs = new HashSet<>();


}

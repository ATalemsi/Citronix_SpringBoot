package com.citronix.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @NotNull
    @Positive(message = "La superficie doit être supérieure à 0.")
    private Double superficie;


    @PastOrPresent
    private LocalDate dateCreation;


    @OneToMany(mappedBy = "ferme" ,cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Champ> champs = new ArrayList<>();


    public Ferme(Long fermeId) {
        this.id = fermeId;
    }
}

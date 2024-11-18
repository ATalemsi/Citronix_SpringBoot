package com.citronix.citronix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "arbres")
public class Arbre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate datePlantation;

    @Transient
    private int agePlantation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;


}

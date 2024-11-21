package com.citronix.citronix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "champ")
public class Champ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  double superficie;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ferme_id" , nullable = false)
    private Ferme ferme;


    @OneToMany(mappedBy = "champ",cascade = CascadeType.ALL)
    private List<Arbre> arbreList;


}

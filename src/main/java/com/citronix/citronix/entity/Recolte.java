package com.citronix.citronix.entity;

import com.citronix.citronix.entity.Enum.Saison;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recolte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Saison saison;


    @NotNull
    private LocalDate dateRecolte;

    @PositiveOrZero
    private Double quantiteTotale;


    @OneToMany(mappedBy = "recolte" , cascade = CascadeType.ALL)
    private List<Recoltedetails> recoltedetailsList;
}

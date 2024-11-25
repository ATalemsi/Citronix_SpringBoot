package com.citronix.citronix.repository;

import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.entity.Recolte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RecolteRepository extends JpaRepository<Recolte, Long> {
    boolean existsBySaisonAndDateRecolte(Saison saison, LocalDate dateRecolte);
}

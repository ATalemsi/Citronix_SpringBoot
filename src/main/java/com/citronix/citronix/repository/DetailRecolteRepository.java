package com.citronix.citronix.repository;

import com.citronix.citronix.entity.Arbre;
import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.entity.Recoltedetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetailRecolteRepository extends JpaRepository<Recoltedetails, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
           "FROM Recoltedetails d " +
            "WHERE d.arbre.id = :arbreId AND d.recolte.saison = :saison")
    boolean existsByArbreAndRecolteSaison(@Param("arbreId") Long arbreId, @Param("saison") Saison saison);

    Optional<Recoltedetails> findByArbreIdAndRecolteSaison(Long arbre_id, Saison recolte_saison);

    List<Recoltedetails> findByRecolteId(Long recolteId);
}

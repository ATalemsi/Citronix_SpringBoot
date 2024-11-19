package com.citronix.citronix.repository;

import com.citronix.citronix.entity.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FermeRepository extends JpaRepository<Ferme, Long>, JpaSpecificationExecutor<Ferme> {
}

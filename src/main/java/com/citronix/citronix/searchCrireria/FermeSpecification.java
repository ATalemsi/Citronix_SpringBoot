package com.citronix.citronix.searchCrireria;

import com.citronix.citronix.dto.search.FermeSearchCriteria;
import com.citronix.citronix.entity.Ferme;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Setter
@Component
public class FermeSpecification implements Specification<Ferme> {

    private FermeSearchCriteria criteria;


    @Override
    public Predicate toPredicate(Root<Ferme> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getNom() != null) {
            predicates.add(cb.like(cb.lower(root.get("nom")), "%" + criteria.getNom().toLowerCase() + "%"));
        }
        if (criteria.getLocalisation() != null) {
            predicates.add(cb.like(cb.lower(root.get("localisation")), "%" + criteria.getLocalisation().toLowerCase() + "%"));
        }
        if (criteria.getMinSuperficie() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("superficie"), criteria.getMinSuperficie()));
        }
        if (criteria.getMaxSuperficie() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("superficie"), criteria.getMaxSuperficie()));
        }
        if (criteria.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreation"), criteria.getStartDate()));
        }
        if (criteria.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("dateCreation"), criteria.getEndDate()));
        }

        // Combine predicates
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

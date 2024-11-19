package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.dto.search.FermeSearchCriteria;
import com.citronix.citronix.entity.Ferme;
import com.citronix.citronix.mapper.FermeMapper;
import com.citronix.citronix.repository.FermeRepository;
import com.citronix.citronix.searchCrireria.FermeSpecification;
import com.citronix.citronix.service.Interface.FermeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FermeServiceImpl implements FermeService {

    private final FermeRepository fermeRepository;
    private final FermeMapper fermeMapper;
    private final FermeSpecification fermeSpecification;



    @Override
    public FermeResponseDto createFerme(@Validated FermeRequestDto dto) {
        Ferme ferme = fermeMapper.toEntity(dto);
        Ferme fermeSaved = fermeRepository.save(ferme);
        return fermeMapper.toDto(fermeSaved);
    }

    @Override
    public Ferme updateFerme(Long id, @Validated FermeRequestDto requestDto) {
        Ferme existingFerme = fermeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ferme not found with id: " + id));

        if (requestDto.getNom() != null) {
            existingFerme.setNom(requestDto.getNom());
        }
        if (requestDto.getLocalisation() != null) {
            existingFerme.setLocalisation(requestDto.getLocalisation());
        }
        if (requestDto.getSuperficie() != null) {
            existingFerme.setSuperficie(requestDto.getSuperficie());
        }
        if (requestDto.getDateCreation() != null) {
            existingFerme.setDateCreation(requestDto.getDateCreation());
        }

        // Save updated entity
        return fermeRepository.save(existingFerme);
    }
    @Override
    public FermeResponseDto getFermeById(Long id) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ferme not found"));
        return fermeMapper.toDto(ferme);
    }

    @Override
    public List<FermeResponseDto> getAllFermes() {
        return fermeRepository.findAll().stream()
                .map(fermeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFerme(Long id) {
        Ferme ferme = fermeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ferme not found"));
        fermeRepository.delete(ferme);

    }

    @Override
    public List<Ferme> searchFermes(FermeSearchCriteria criteria) {
        fermeSpecification.setCriteria(criteria);
        return fermeRepository.findAll(fermeSpecification);
    }
}

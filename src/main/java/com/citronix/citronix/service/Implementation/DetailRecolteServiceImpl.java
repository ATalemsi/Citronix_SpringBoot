package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.DetailRecolteRequestDto;
import com.citronix.citronix.dto.Response.DetailRecolteResponseDto;
import com.citronix.citronix.entity.Arbre;
import com.citronix.citronix.entity.Recolte;
import com.citronix.citronix.entity.Recoltedetails;
import com.citronix.citronix.mapper.DetailRecolteMapper;
import com.citronix.citronix.repository.ArbreRepository;
import com.citronix.citronix.repository.DetailRecolteRepository;
import com.citronix.citronix.repository.RecolteRepository;
import com.citronix.citronix.service.Interface.DetailRecolteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DetailRecolteServiceImpl implements DetailRecolteService {

    private final DetailRecolteRepository detailRecolteRepository;
    private final DetailRecolteMapper detailRecolteMapper;
    private final ArbreRepository arbreRepository;
    private final RecolteRepository recolteRepository;


    private void validateDetailRecolteConstraints(Long arbreId ,Long recolteId){

        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new IllegalArgumentException("recolte id " + recolteId + " not found"));

        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        boolean alreadyHarvested = detailRecolteRepository.existsByArbreAndRecolteSaison(arbre.getId(),recolte.getSaison());

        if (alreadyHarvested){
            throw new IllegalArgumentException("Arbre " + arbreId + " already harvested for this season.");
        }
    }

    @Override
    public DetailRecolteResponseDto addDetailRecolte(DetailRecolteRequestDto detailRecolteRequestDto) {
        validateDetailRecolteConstraints(detailRecolteRequestDto.getArbreId(),detailRecolteRequestDto.getRecolteId());

        Recoltedetails recoltedetails = detailRecolteMapper.toEntity(detailRecolteRequestDto);

        Recolte recolte = recolteRepository.findById(detailRecolteRequestDto.getRecolteId())
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));
        recoltedetails.setRecolte(recolte);

        Arbre arbre = arbreRepository.findById(detailRecolteRequestDto.getArbreId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        recoltedetails.setArbre(arbre);

        Recoltedetails savedDetailRecolte = detailRecolteRepository.save(recoltedetails);


        return detailRecolteMapper.toDto(savedDetailRecolte);
    }
}

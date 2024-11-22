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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DetailRecolteServiceImpl implements DetailRecolteService {

    private final DetailRecolteRepository detailRecolteRepository;
    private final DetailRecolteMapper detailRecolteMapper;
    private final ArbreRepository arbreRepository;
    private final RecolteRepository recolteRepository;


    private void validateDetailRecolteConstraints(Long arbreId, Long recolteId, Long currentDetailId) {
        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));

        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        if (!arbre.getChamp().getId().equals(recolte.getChamp().getId())) {
            throw new IllegalArgumentException(
                    "Arbre " + arbreId + " does not belong to Champ " + recolte.getChamp().getId() + " of Recolte " + recolteId
            );
        }

        Optional<Recoltedetails> existingDetail = detailRecolteRepository.findByArbreIdAndRecolteSaison(arbre.getId(), recolte.getSaison());

        if (existingDetail.isPresent()) {
            if (currentDetailId == null || !existingDetail.get().getId().equals(currentDetailId)) {
                throw new IllegalArgumentException("Arbre " + arbreId + " already harvested for this season.");
            }
        }
    }


    @Override
    public DetailRecolteResponseDto addDetailRecolte(DetailRecolteRequestDto detailRecolteRequestDto) {
        validateDetailRecolteConstraints(detailRecolteRequestDto.getArbreId(),detailRecolteRequestDto.getRecolteId(),null);

        Recoltedetails recoltedetails = detailRecolteMapper.toEntity(detailRecolteRequestDto);

        Recolte recolte = recolteRepository.findById(detailRecolteRequestDto.getRecolteId())
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));
        recoltedetails.setRecolte(recolte);

        Arbre arbre = arbreRepository.findById(detailRecolteRequestDto.getArbreId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        recoltedetails.setArbre(arbre);

        Recoltedetails savedDetailRecolte = detailRecolteRepository.save(recoltedetails);

        double totalQuantity = calculateAndUpdateTotalQuantity(recolte.getId());
        recolte.setQuantiteTotale(totalQuantity);

        recolteRepository.save(recolte);


        return detailRecolteMapper.toDto(savedDetailRecolte);
    }

    @Override
    public List<DetailRecolteResponseDto> getAllDetails() {
        List<Recoltedetails> details = detailRecolteRepository.findAll();
        return details.stream()
                .map(detailRecolteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DetailRecolteResponseDto getDetailRecolteById(Long id) {
        Recoltedetails recoltedetail = detailRecolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("DetailRecolte not found with id " + id));
        return detailRecolteMapper.toDto(recoltedetail);
    }

    @Override
    public DetailRecolteResponseDto updateDetailRecolte(Long id, DetailRecolteRequestDto detailRecolteRequestDto) {
        Recoltedetails existingRecoltedetail = detailRecolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("DetailRecolte not found with id " + id));

        validateDetailRecolteConstraints(detailRecolteRequestDto.getArbreId(), detailRecolteRequestDto.getRecolteId(), id);

        Recolte recolte = recolteRepository.findById(detailRecolteRequestDto.getRecolteId())
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));
        existingRecoltedetail.setRecolte(recolte);

        Arbre arbre = arbreRepository.findById(detailRecolteRequestDto.getArbreId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));
        existingRecoltedetail.setArbre(arbre);

        existingRecoltedetail.setQuantite(detailRecolteRequestDto.getQuantite());

        Recoltedetails updatedDetailRecolte = detailRecolteRepository.save(existingRecoltedetail);
        return detailRecolteMapper.toDto(updatedDetailRecolte);
    }


    @Override
    public void deleteDetailRecolte(Long id) {
        Recoltedetails recoltedetail = detailRecolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("DetailRecolte not found with id " + id));
        detailRecolteRepository.delete(recoltedetail);
    }

    @Override
    public double calculateAndUpdateTotalQuantity(Long recolteId) {
        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));
        List<Recoltedetails> details = detailRecolteRepository.findByRecolteId(recolteId);

        double totalQuantity = details.stream()
                .mapToDouble(Recoltedetails::getQuantite)
                .sum();
        recolte.setQuantiteTotale(totalQuantity);
        recolteRepository.save(recolte);

        return totalQuantity;
    }
}

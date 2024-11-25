package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.entity.Recolte;
import com.citronix.citronix.mapper.ChampMapper;
import com.citronix.citronix.mapper.RecolteMapper;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.repository.RecolteRepository;
import com.citronix.citronix.service.Interface.RecolteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecolteServiceImpl implements RecolteService {

    private final RecolteRepository recolteRepository;
    private final ChampRepository champRepository;
    private final RecolteMapper recolteMapper;

    private final ChampMapper champMapper;


    @Override
    public void validateRecolteConstraints(Saison saison, LocalDate dateRecolte) {
        boolean exists = recolteRepository.existsBySaisonAndDateRecolte(saison, dateRecolte);

        if (exists) {
            throw new IllegalArgumentException("A recolte already exists for the given season and date.");
        }
    }

    @Override
    public RecolteResponseDto addRecolte(RecolteRequestDto recolteRequestDto) {

        Saison saison = determineSaison(recolteRequestDto.getDateRecolte());
        recolteRequestDto.setSaison(saison);
        validateRecolteConstraints(recolteRequestDto.getSaison(), recolteRequestDto.getDateRecolte());

        Recolte recolte = recolteMapper.toEntity(recolteRequestDto);
        Recolte savedRecolte  = recolteRepository.save(recolte);

        return recolteMapper.toDto(savedRecolte);
    }

    @Override
    public RecolteResponseDto updateRecolte(Long id, RecolteRequestDto recolteRequestDto) {
        Recolte existingRecolte = recolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));


        if (recolteRequestDto.getDateRecolte() != null) {
            Saison calculatedSaison = determineSaison(recolteRequestDto.getDateRecolte());

            if (recolteRequestDto.getSaison() == null || !recolteRequestDto.getSaison().equals(existingRecolte.getSaison())) {
                recolteRequestDto.setSaison(calculatedSaison);
            }
            existingRecolte.setDateRecolte(recolteRequestDto.getDateRecolte());
        }

        if (recolteRequestDto.getSaison() != null && !recolteRequestDto.getSaison().equals(existingRecolte.getSaison())) {
            validateRecolteConstraints(recolteRequestDto.getSaison(), recolteRequestDto.getDateRecolte());
            existingRecolte.setSaison(recolteRequestDto.getSaison());
        }

        if (existingRecolte.getRecoltedetailsList() == null) {
            existingRecolte.setRecoltedetailsList(new ArrayList<>());
        }
        Recolte updatedRecolte = recolteRepository.save(existingRecolte);

        return recolteMapper.toDto(updatedRecolte);
    }

    @Override
    public void deleteRecolte(Long id) {
        if (!recolteRepository.existsById(id)) {
            throw new IllegalArgumentException("Recolte not found");
        }
        recolteRepository.deleteById(id);

    }

    @Override
    public RecolteResponseDto getRecolteById(Long id) {
        Recolte recolte = recolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));

        return recolteMapper.toDto(recolte);
    }

    @Override
    public List<RecolteResponseDto> getAllRecoltes() {
        List<Recolte> recoltes = recolteRepository.findAll();
        return recoltes.stream()
                .map(recolteMapper::toDto)
                .collect(Collectors.toList());
    }

    private Saison determineSaison(LocalDate dateRecolte) {
        int month = dateRecolte.getMonthValue();
        if (month == 12 || month == 1 || month == 2) {
            return Saison.HIVER;
        } else if (month >= 3 && month <= 5) {
            return Saison.PRINTEMPS;
        } else if (month >= 6 && month <= 8) {
            return Saison.ETE;
        } else {
            return Saison.AUTOMME;
        }
    }

}

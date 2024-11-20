package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.ChampRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Ferme;
import com.citronix.citronix.mapper.ChampMapper;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.repository.FermeRepository;
import com.citronix.citronix.service.Interface.ChampService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChampServiceImpl implements ChampService {

    private final ChampRepository champRepository;
    private final ChampMapper champMapper;
    private final FermeRepository fermeRepository;


    @Override
    public ChampResponseDto addChamp(ChampRequestDto champRequestDto) {
        Ferme ferme = fermeRepository.findById(champRequestDto.getFermeId())
                .orElseThrow(() -> new IllegalArgumentException("Ferme not found"));

        Champ champ = champMapper.toEntity(champRequestDto);
        validateSuperficie(champ, ferme);
        validateChampCount(ferme);
        champ.setFerme(ferme);
        Champ champSaved = champRepository.save(champ);
        return champMapper.toDto(champSaved);
    }

    @Override
    public ChampResponseDto updateChamp(Long champId, ChampRequestDto champRequestDto) {
        Champ existingChamp = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));
        Ferme ferme = existingChamp.getFerme();
        if (champRequestDto.getSuperficie() != null) {
            existingChamp.setSuperficie(champRequestDto.getSuperficie());
        }
        if (champRequestDto.getFermeId() != null) {
            Ferme newFerme = fermeRepository.findById(champRequestDto.getFermeId())
                    .orElseThrow(() -> new IllegalArgumentException("Ferme not found"));
            existingChamp.setFerme(newFerme);
        }
        validateSuperficie(existingChamp, ferme);

        Champ updatedChamp = champRepository.save(existingChamp);
        return champMapper.toDto(updatedChamp);
    }

    @Override
    public List<ChampResponseDto> getAllChamps() {
        List<Champ> champs = champRepository.findAll();
        return champMapper.toDtoList(champs);
    }

    @Override
    public ChampResponseDto getChampById(Long champId) {
        Champ champ = champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));
        return champMapper.toDto(champ);
    }

    @Override
    public void deleteChamp(Long champId) {
        Champ existingChamp= champRepository.findById(champId)
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));

        champRepository.delete(existingChamp);
    }

    private void validateSuperficie(Champ champ, Ferme ferme) {

        if (champ.getSuperficie() <= 0) {
            throw new IllegalArgumentException("La superficie doit être supérieure à 0");
        }

        if (champ.getSuperficie() > ferme.getSuperficie() * 0.5) {
            throw new IllegalArgumentException("La superficie d'un champ ne peut pas dépasser 50% de la superficie de la ferme.");
        }

        double totalSuperficieChamps = ferme.getChamps().stream().mapToDouble(Champ::getSuperficie).sum();
        if (totalSuperficieChamps + champ.getSuperficie() > ferme.getSuperficie()) {
            throw new IllegalArgumentException("La somme des superficies des champs ne peut pas dépasser la superficie totale de la ferme.");
        }
    }

    private void validateChampCount(Ferme ferme) {
        if (ferme.getChamps().size() >= 10) {
            throw new IllegalArgumentException("Une ferme ne peut contenir plus de 10 champs.");
        }
    }


}

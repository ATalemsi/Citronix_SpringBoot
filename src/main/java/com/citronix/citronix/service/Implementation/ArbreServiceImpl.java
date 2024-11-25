package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.ArbreRequestDto;
import com.citronix.citronix.dto.Response.ArbreResponseDto;
import com.citronix.citronix.entity.Arbre;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.mapper.ArbreMapper;
import com.citronix.citronix.repository.ArbreRepository;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.service.Interface.ArbreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArbreServiceImpl implements ArbreService {


    private final ArbreRepository arbreRepository;
    private final ArbreMapper arbreMapper;
    private final ChampRepository champRepository;

    @Override
    public ArbreResponseDto addArbre(ArbreRequestDto arbreRequestDto) {
        Champ champ = champRepository.findById(arbreRequestDto.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));

        validatePlantingPeriod(arbreRequestDto.getDatePlantation());
        validateArbreDensity(champ);

        Arbre arbre = arbreMapper.toEntity(arbreRequestDto);
        arbre.setChamp(champ);

        arbre = arbreRepository.save(arbre);

        Arbre savedArbre = arbreRepository.findById(arbre.getId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        int age = calculateAge(savedArbre);
        savedArbre.setAgePlantation(age);

        String productivite = calculateAndSetProductivite(age);
        savedArbre.setProductivite(productivite);

        savedArbre = arbreRepository.save(savedArbre);

        return arbreMapper.toDto(savedArbre);
    }

    @Override
    public List<ArbreResponseDto> getAllArbres() {
        List<Arbre> arbres = arbreRepository.findAll();
        return arbres.stream()
                .map(arbreMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteArbre(Long arbreId) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        arbreRepository.delete(arbre);
    }

    private int calculateAge(Arbre arbre) {
        return Period.between(arbre.getDatePlantation(), LocalDate.now()).getYears();
    }

    private String calculateAndSetProductivite(int agePlantation) {
        if (agePlantation == 0) {
            throw new IllegalArgumentException("Age of plantation is not set for arbre ");
        }

        if (agePlantation < 3) {
            return "2.5 kg / saison";
        } else if (agePlantation <= 10) {
            return "12 kg / saison";
        } else {
            return "20 kg / saison";
        }
    }


    private void validateArbreDensity(Champ champ) {

        long currentArbresCount = champ.getArbreList().size();

        long maxArbresAllowed = (long) (champ.getSuperficie() * 100);


        if (currentArbresCount >= maxArbresAllowed) {
            throw new IllegalArgumentException(
                    "Le nombre d'arbres dépasse la densité maximale autorisée de 100 arbres par hectare."
            );
        }
    }

    private void validatePlantingPeriod(LocalDate datePlantation) {
        int month = datePlantation.getMonthValue();
        if (month < 3 || month > 5) {
            throw new IllegalArgumentException("Les arbres ne peuvent être plantés qu'entre mars et mai.");
        }
    }


}

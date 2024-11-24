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

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArbreServiceImpl implements ArbreService {


    private final ArbreRepository arbreRepository;
    private final ArbreMapper arbreMapper;
    private final ChampRepository champRepository;


    public ArbreResponseDto addArbre(ArbreRequestDto arbreRequestDto) {
        Champ champ = champRepository.findById(arbreRequestDto.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));

        validatePlantingPeriod(arbreRequestDto.getDatePlantation());
        validateArbreDensity(champ);

        Arbre arbre = arbreMapper.toEntity(arbreRequestDto);
        arbre.setChamp(champ);

        arbre = arbreRepository.save(arbre);

        // Retrieve the saved arbre once
        Arbre savedArbre = arbreRepository.findById(arbre.getId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));

        int age = calculateAge(savedArbre);
        savedArbre.setAgePlantation(age);

        String productivite = calculateAndSetProductivite(savedArbre);
        savedArbre.setProductivite(productivite);

        savedArbre = arbreRepository.save(savedArbre);

        return arbreMapper.toDto(savedArbre);
    }

    private int calculateAge(Arbre arbre) {
        return Period.between(arbre.getDatePlantation(), LocalDate.now()).getYears();
    }

    private String calculateAndSetProductivite(Arbre arbre) {
        Integer age = arbre.getAgePlantation();
        if (age == null) {
            throw new IllegalArgumentException("Age of plantation is not set for arbre with id: " + arbre.getId());
        }

        if (age < 3) {
            return "2.5 kg / saison";
        } else if (age <= 10) {
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

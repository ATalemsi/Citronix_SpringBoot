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


    @Override
    public ArbreResponseDto addArbre(ArbreRequestDto arbreRequestDto) {
        Champ champ = champRepository.findById(arbreRequestDto.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));

        validatePlantingPeriod(arbreRequestDto.getDatePlantation());
        validateArbreDensity(champ);

        Arbre arbre = arbreMapper.toEntity(arbreRequestDto);
        arbre.setChamp(champ);

        Arbre arbreSaved = arbreRepository.save(arbre);

        int age = calculateAge(arbreSaved.getId());
        arbreSaved.setAgePlantation(age);


        String productivite = calculateAndSetProductivite(arbreSaved.getId());

        arbreSaved.setAgePlantation(age);
        arbreSaved.setProductivite(productivite);

        arbreRepository.save(arbreSaved);

        return arbreMapper.toDto(arbreSaved);
    }

    @Override
    public int calculateAge(Long arbreId) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));
        int age = Period.between(arbre.getDatePlantation(), LocalDate.now()).getYears();
        arbre.setAgePlantation(age);
        arbreRepository.save(arbre);
        return age;
    }

    @Override
    public String calculateAndSetProductivite(Long arbreId) {
        Arbre arbre = arbreRepository.findById(arbreId)
                .orElseThrow(() -> new IllegalArgumentException("Arbre not found"));
        int age = arbre.getAgePlantation();

        String productivite;
        if (age < 3) {
            productivite = "2.5 kg / saison";
        } else if (age <= 10) {
            productivite = "12 kg / saison";
        } else {
            productivite = "20 kg / saison";
        }

        arbre.setProductivite(productivite);
        arbreRepository.save(arbre);

        return productivite;
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

package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.entity.Recolte;
import com.citronix.citronix.mapper.RecolteMapper;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.repository.RecolteRepository;
import com.citronix.citronix.service.Interface.RecolteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecolteServiceImpl implements RecolteService {

    private final RecolteRepository recolteRepository;
    private final ChampRepository champRepository;
    private final RecolteMapper recolteMapper;


    @Override
    public void validateRecolteConstraints(Champ champ, Saison saison) {
        boolean exists = recolteRepository.existsByChampAndSaison(champ, saison);

        if (exists){
            throw new IllegalArgumentException("A récolte already exists for this champ in the given season.");
        }

    }

    @Override
    public RecolteResponseDto addRecolte(RecolteRequestDto recolteRequestDto) {
        Champ champ = champRepository.findById(recolteRequestDto.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ not found"));

        validateRecolteConstraints(champ,recolteRequestDto.getSaison());

        Recolte recolte = recolteMapper.toEntity(recolteRequestDto);
        recolte.setChamp(champ);


        Recolte savedRecolte  = recolteRepository.save(recolte);

        return recolteMapper.toDto(savedRecolte);
    }

}

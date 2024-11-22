package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;

import java.util.List;

public interface RecolteService {
    void validateRecolteConstraints(Champ champ, Saison saison);
    RecolteResponseDto addRecolte(RecolteRequestDto recolteRequestDto);
    RecolteResponseDto updateRecolte(Long id, RecolteRequestDto recolteRequestDto);
    void deleteRecolte(Long id);
    RecolteResponseDto getRecolteById(Long id);
    List<RecolteResponseDto> getAllRecoltes();
}

package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.dto.search.FermeSearchCriteria;
import com.citronix.citronix.entity.Ferme;

import java.util.List;

public interface FermeService {
    FermeResponseDto createFerme(FermeRequestDto dto);
    Ferme updateFerme(Long id, FermeRequestDto dto);
    FermeResponseDto getFermeById(Long id);
    List<FermeResponseDto> getAllFermes();
    void deleteFerme(Long id);
    List<Ferme> searchFermes(FermeSearchCriteria criteria);
}

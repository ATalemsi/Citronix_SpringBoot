package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.dto.updateDto.UpdateVenteDto;

import java.util.List;

public interface VenteService {
    VenteResponseDto addVente(VenteRequestDto venteRequestDto);
    List<VenteResponseDto> getAllVentes();
    VenteResponseDto getVenteById(Long id);
    void deleteVente(Long id);
    VenteResponseDto updateVente(Long id,UpdateVenteDto updateVenteDto);

}

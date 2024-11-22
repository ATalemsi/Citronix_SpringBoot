package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;

public interface VenteService {
    VenteResponseDto addVente(VenteRequestDto venteRequestDto);
}

package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.ArbreRequestDto;
import com.citronix.citronix.dto.Response.ArbreResponseDto;

import java.util.List;

public interface ArbreService {
    ArbreResponseDto addArbre(ArbreRequestDto arbreRequestDto);
    List<ArbreResponseDto> getAllArbres();
    void deleteArbre(Long arbreId);
}

package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.ArbreRequestDto;
import com.citronix.citronix.dto.Response.ArbreResponseDto;

public interface ArbreService {
    ArbreResponseDto addArbre(ArbreRequestDto arbreRequestDto);

}

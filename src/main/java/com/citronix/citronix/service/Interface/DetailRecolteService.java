package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.DetailRecolteRequestDto;
import com.citronix.citronix.dto.Response.DetailRecolteResponseDto;

public interface DetailRecolteService {
    DetailRecolteResponseDto addDetailRecolte(DetailRecolteRequestDto detailRecolteRequestDto);
}

package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.DetailRecolteRequestDto;
import com.citronix.citronix.dto.Response.DetailRecolteResponseDto;

import java.util.List;

public interface DetailRecolteService {
    DetailRecolteResponseDto addDetailRecolte(DetailRecolteRequestDto detailRecolteRequestDto);
    List<DetailRecolteResponseDto> getAllDetails();
    DetailRecolteResponseDto getDetailRecolteById(Long id);
    DetailRecolteResponseDto updateDetailRecolte(Long id, DetailRecolteRequestDto detailRecolteRequestDto);
    void deleteDetailRecolte(Long id);
    double calculateAndUpdateTotalQuantity(Long recolteId);

}

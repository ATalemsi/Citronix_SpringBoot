package com.citronix.citronix.service.Interface;

import com.citronix.citronix.dto.Request.ChampRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;

import java.util.List;

public interface ChampService {
    ChampResponseDto addChamp(ChampRequestDto champRequestDto);
    ChampResponseDto updateChamp(Long champId, ChampRequestDto champRequestDto);
    List<ChampResponseDto> getAllChamps();
    ChampResponseDto getChampById(Long champId);
    void deleteChamp(Long champId);
}

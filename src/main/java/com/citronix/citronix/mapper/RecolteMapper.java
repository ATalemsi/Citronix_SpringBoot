package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.entity.Recolte;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetailRecolteMapper.class})
public interface RecolteMapper {
    RecolteResponseDto toDto(Recolte recolte);

    Recolte toEntity(RecolteRequestDto recolteRequestDto);

    List<RecolteResponseDto> toDtoList(List<Recolte> recoltes);
}

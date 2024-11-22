package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.DetailRecolteRequestDto;
import com.citronix.citronix.dto.Response.DetailRecolteResponseDto;
import com.citronix.citronix.entity.Recoltedetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses ={ ArbreMapper.class , RecolteMapper.class })
public interface DetailRecolteMapper {

    @Mapping(target = "arbreId", source = "arbre.id")
    @Mapping(target = "recolteId", source = "recolte.id")
    DetailRecolteResponseDto toDto(Recoltedetails detailRecolte);

    Recoltedetails toEntity(DetailRecolteRequestDto detailRecolteRequestDto);

    List<DetailRecolteResponseDto> toDtoList(List<Recoltedetails> detailsRecolte);
}

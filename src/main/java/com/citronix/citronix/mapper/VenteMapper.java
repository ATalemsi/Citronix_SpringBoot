package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.entity.Vente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RecolteMapper.class)
public interface VenteMapper {
    VenteResponseDto toDto(Vente vente);

    Vente toEntity(VenteRequestDto venteRequestDto);

    List<VenteResponseDto> toDtoList(List<Vente> ventes);
}

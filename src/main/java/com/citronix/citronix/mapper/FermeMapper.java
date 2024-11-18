package com.citronix.citronix.mapper;
import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.entity.Ferme;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FermeMapper {

    FermeResponseDto toDto(Ferme ferme);

    Ferme toEntity(FermeRequestDto fermeRequestDto);

    List<FermeResponseDto> toDtoList(List<Ferme> fermes);
}

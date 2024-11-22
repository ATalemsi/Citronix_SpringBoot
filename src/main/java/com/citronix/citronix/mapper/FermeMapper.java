package com.citronix.citronix.mapper;
import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.entity.Ferme;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = ChampMapper.class)
public interface FermeMapper {


    @Mapping(target = "champs", source = "champs")
    FermeResponseDto toDto(Ferme ferme);

    Ferme toEntity(FermeRequestDto fermeRequestDto);

    List<FermeResponseDto> toDtoList(List<Ferme> fermes);
}

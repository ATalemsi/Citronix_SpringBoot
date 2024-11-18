package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.ChampRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.entity.Champ;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = FermeMapper.class)
public interface ChampMapper {
    ChampResponseDto toDto(Champ champ);

    Champ toEntity(ChampRequestDto champRequestDto);

    List<ChampResponseDto> toDtoList(List<Champ> champs);
}

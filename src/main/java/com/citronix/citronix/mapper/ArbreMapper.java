package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.ArbreRequestDto;
import com.citronix.citronix.dto.Response.ArbreResponseDto;
import com.citronix.citronix.entity.Arbre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ChampMapper.class)
public interface ArbreMapper {

    @Mapping(target = "champResponseDto", source = "champ")
    ArbreResponseDto toDto(Arbre arbre);

    @Mapping(target = "champ.id", source = "champId")
    Arbre toEntity(ArbreRequestDto arbreRequestDto);

    List<ArbreResponseDto> toDtoList(List<Arbre> arbres);
}

package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.ChampRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Ferme;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring", uses = FermeMapper.class)
public interface ChampMapper {

    @Mapping(target = "fermeNom", source = "ferme.nom")
    @Mapping(target = "localisation", source = "ferme.localisation")
    ChampResponseDto toDto(Champ champ);

    @Mapping(target = "ferme", expression = "java(mapFermeIdToFerme(champRequestDto.getFermeId()))")
    Champ toEntity(ChampRequestDto champRequestDto);

    List<ChampResponseDto> toDtoList(List<Champ> champs);

    default Ferme mapFermeIdToFerme(Long fermeId) {
        if (fermeId == null) {
            return null;
        }
        Ferme ferme = new Ferme();
        ferme.setId(fermeId);
        return ferme;
    }
}

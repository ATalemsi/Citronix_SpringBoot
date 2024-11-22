package com.citronix.citronix.mapper;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = RecolteMapper.class)
public interface VenteMapper {
    @Mapping(source = "quantiteVendue", target = "quantiteVendue")
    @Mapping(source = "dateVente", target = "dateVente")
    Vente toEntity(VenteRequestDto venteRequestDto);


    @Mapping(source = "revenue", target = "revenu")
    @Mapping(source = "recolte.id", target = "recolteId")
    @Mapping(source = "recolte.saison", target = "recolteSaison")
    @Mapping(source = "client.name", target = "clientName")
    @Mapping(source = "client.email", target = "clientEmail")
    VenteResponseDto toDto(Vente vente);

    List<VenteResponseDto> toDtoList(List<Vente> ventes);
}

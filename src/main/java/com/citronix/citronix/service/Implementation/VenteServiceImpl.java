package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.entity.Client;
import com.citronix.citronix.entity.Recolte;
import com.citronix.citronix.entity.Vente;
import com.citronix.citronix.mapper.VenteMapper;
import com.citronix.citronix.repository.RecolteRepository;
import com.citronix.citronix.repository.VenteRepository;
import com.citronix.citronix.service.Interface.VenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VenteServiceImpl implements VenteService {

    private final VenteRepository venteRepository;
    private final RecolteRepository recolteRepository;
    private final VenteMapper venteMapper;
    @Override
    public VenteResponseDto addVente(VenteRequestDto venteRequestDto) {
        if (venteRequestDto.getQuantiteVendue() == null || venteRequestDto.getQuantiteVendue() <= 0) {
            throw new IllegalArgumentException("Quantité vendue ne peut pas être nulle ou négative");
        }
        if (venteRequestDto.getDateVente() == null) {
            throw new IllegalArgumentException("Date de vente ne peut pas être nulle");
        }
        Client client = new Client(venteRequestDto.getClientName(), venteRequestDto.getClientEmail());

        Recolte recolte = recolteRepository.findById(venteRequestDto.getRecolteId())
                .orElseThrow(() -> new IllegalArgumentException("Recolte not found"));

        if (recolte.getQuantiteTotale() < venteRequestDto.getQuantiteVendue()) {
            throw new IllegalArgumentException("Insufficient quantity in Recolte");
        }

        recolte.setQuantiteTotale(recolte.getQuantiteTotale() - venteRequestDto.getQuantiteVendue());


        Vente vente = venteMapper.toEntity(venteRequestDto);
        vente.setClient(client);
        vente.setRecolte(recolte);
        vente.setRevenue(venteRequestDto.getQuantiteVendue() * venteRequestDto.getPrixUnitaire());
        Vente savedVente = venteRepository.save(vente);

        recolteRepository.save(recolte);

        return venteMapper.toDto(savedVente);
    }
}

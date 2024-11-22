package com.citronix.citronix.service.Implementation;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.dto.updateDto.UpdateVenteDto;
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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<VenteResponseDto> getAllVentes() {
        return venteRepository.findAll()
                .stream()
                .map(venteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VenteResponseDto getVenteById(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente not found"));
        return venteMapper.toDto(vente);
    }

    @Override
    public void deleteVente(Long id) {
        if (!venteRepository.existsById(id)) {
            throw new IllegalArgumentException("Vente not found");
        }
        venteRepository.deleteById(id);

    }

    @Override
    public VenteResponseDto updateVente(Long id,UpdateVenteDto updateVenteDto) {
        Vente existingVente = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente not found"));

        if (updateVenteDto.getDateVente() != null) {
            existingVente.setDateVente(updateVenteDto.getDateVente());
        }
        if (updateVenteDto.getQuantiteVendue() != null && updateVenteDto.getQuantiteVendue() > 0) {
            Recolte recolte = existingVente.getRecolte();
            double previousQuantiteVendue = existingVente.getQuantiteVendue();

            double availableQuantite = recolte.getQuantiteTotale() + previousQuantiteVendue;
            if (availableQuantite < updateVenteDto.getQuantiteVendue()) {
                throw new IllegalArgumentException("Insufficient quantity in Recolte");
            }
            recolte.setQuantiteTotale(availableQuantite - updateVenteDto.getQuantiteVendue());
            recolteRepository.save(recolte);

            existingVente.setQuantiteVendue(updateVenteDto.getQuantiteVendue());
        }
        if (updateVenteDto.getPrixUnitaire() != null && updateVenteDto.getPrixUnitaire() > 0) {
            existingVente.setPrixUnitaire(updateVenteDto.getPrixUnitaire());
        }

        Double quantiteVendue = existingVente.getQuantiteVendue();
        Double prixUnitaire = existingVente.getPrixUnitaire();
        if (quantiteVendue != null && prixUnitaire != null) {
            existingVente.setRevenue(quantiteVendue * prixUnitaire);
        }

        if (updateVenteDto.getClientName() != null) {
            existingVente.getClient().setName(updateVenteDto.getClientName());
        }
        if (updateVenteDto.getClientEmail() != null) {
            existingVente.getClient().setEmail(updateVenteDto.getClientEmail());
        }

        Vente updatedVente = venteRepository.save(existingVente);

        return venteMapper.toDto(updatedVente);
    }
}

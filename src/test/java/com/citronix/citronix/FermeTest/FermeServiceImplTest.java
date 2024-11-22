package com.citronix.citronix.FermeTest;

import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.entity.Ferme;
import com.citronix.citronix.mapper.FermeMapper;
import com.citronix.citronix.repository.FermeRepository;
import com.citronix.citronix.searchCrireria.FermeSpecification;
import com.citronix.citronix.service.Implementation.FermeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FermeServiceImplTest {
    @InjectMocks
    private FermeServiceImpl fermeService;

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private FermeMapper fermeMapper;

    @Mock
    private FermeSpecification fermeSpecification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFerme() {
        FermeRequestDto requestDto = new FermeRequestDto();
        requestDto.setNom("Ferme A");
        requestDto.setLocalisation("Localisation A");
        requestDto.setSuperficie(100.5);

        Ferme ferme = new Ferme();
        Ferme savedFerme = new Ferme();
        FermeResponseDto responseDto = new FermeResponseDto();

        when(fermeMapper.toEntity(requestDto)).thenReturn(ferme);
        when(fermeRepository.save(ferme)).thenReturn(savedFerme);
        when(fermeMapper.toDto(savedFerme)).thenReturn(responseDto);

        FermeResponseDto result = fermeService.createFerme(requestDto);

        assertNotNull(result);
        verify(fermeMapper, times(1)).toEntity(requestDto);
        verify(fermeRepository, times(1)).save(ferme);
        verify(fermeMapper, times(1)).toDto(savedFerme);
    }

    @Test
    void testGetFermeById() {
        Long id = 1L;
        Ferme ferme = new Ferme();
        FermeResponseDto responseDto = new FermeResponseDto();

        when(fermeRepository.findById(id)).thenReturn(Optional.of(ferme));
        when(fermeMapper.toDto(ferme)).thenReturn(responseDto);

        FermeResponseDto result = fermeService.getFermeById(id);

        assertNotNull(result);
        verify(fermeRepository, times(1)).findById(id);
        verify(fermeMapper, times(1)).toDto(ferme);
    }

    @Test
    void testUpdateFerme() {
        Long id = 1L;
        Ferme existingFerme = new Ferme();
        existingFerme.setNom("Old Name");

        FermeRequestDto requestDto = new FermeRequestDto();
        requestDto.setNom("New Name");

        when(fermeRepository.findById(id)).thenReturn(Optional.of(existingFerme));
        when(fermeRepository.save(existingFerme)).thenReturn(existingFerme);

        Ferme result = fermeService.updateFerme(id, requestDto);

        assertNotNull(result);
        assertEquals("New Name", result.getNom());
        verify(fermeRepository, times(1)).findById(id);
        verify(fermeRepository, times(1)).save(existingFerme);
    }

    @Test
    void testDeleteFerme() {
        Long id = 1L;
        Ferme ferme = new Ferme();

        when(fermeRepository.findById(id)).thenReturn(Optional.of(ferme));

        fermeService.deleteFerme(id);

        verify(fermeRepository, times(1)).findById(id);
        verify(fermeRepository, times(1)).delete(ferme);
    }

    @Test
    void testGetAllFermes() {
        List<Ferme> fermes = List.of(new Ferme());
        List<FermeResponseDto> responseDtos = List.of(new FermeResponseDto());

        when(fermeRepository.findAll()).thenReturn(fermes);
        when(fermeMapper.toDtoList(fermes)).thenReturn(responseDtos);

        List<FermeResponseDto> result = fermeService.getAllFermes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(fermeRepository, times(1)).findAll();
        verify(fermeMapper, times(1)).toDtoList(fermes);
    }
}

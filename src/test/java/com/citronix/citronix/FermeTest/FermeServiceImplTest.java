package com.citronix.citronix.FermeTest;

import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.entity.Ferme;
import com.citronix.citronix.mapper.FermeMapper;
import com.citronix.citronix.repository.FermeRepository;
import com.citronix.citronix.service.Implementation.FermeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FermeServiceImplTest {

    @InjectMocks
    private FermeServiceImpl fermeService;

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private FermeMapper fermeMapper;

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
        when(fermeRepository.save(any(Ferme.class))).thenReturn(savedFerme);
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
        ferme.setId(id);
        FermeResponseDto responseDto = new FermeResponseDto();
        responseDto.setId(id);

        when(fermeRepository.findById(id)).thenReturn(Optional.of(ferme));
        when(fermeMapper.toDto(ferme)).thenReturn(responseDto);

        FermeResponseDto result = fermeService.getFermeById(id);

        assertNotNull(result);
        verify(fermeRepository, times(1)).findById(id);
        verify(fermeMapper, times(1)).toDto(ferme);
    }

    @Test
    void testDeleteFerme() {
        Long id = 1L;
        Ferme ferme = new Ferme();
        ferme.setId(id);

        when(fermeRepository.findById(id)).thenReturn(Optional.of(ferme));

        fermeService.deleteFerme(id);

        verify(fermeRepository, times(1)).findById(id);
        verify(fermeRepository, times(1)).delete(ferme);
    }

    @Test
    void testGetAllFermes() {

        Ferme ferme1 = new Ferme();
        ferme1.setId(1L);
        ferme1.setNom("Ferme1");
        ferme1.setLocalisation("Location1");
        ferme1.setSuperficie(100.0);
        ferme1.setDateCreation(LocalDate.parse("2024-01-01"));

        Ferme ferme2 = new Ferme();
        ferme2.setId(2L);
        ferme2.setNom("Ferme2");
        ferme2.setLocalisation("Location2");
        ferme2.setSuperficie(150.0);
        ferme2.setDateCreation(LocalDate.parse("2024-02-01"));

        List<Ferme> fermes = Arrays.asList(ferme1, ferme2);


        FermeResponseDto fermeResponseDto1 = new FermeResponseDto();
        fermeResponseDto1.setId(1L);
        fermeResponseDto1.setNom("Ferme1");
        fermeResponseDto1.setLocalisation("Location1");
        fermeResponseDto1.setSuperficie(100.0);
        fermeResponseDto1.setDateCreation(LocalDate.parse("2024-01-01"));

        FermeResponseDto fermeResponseDto2 = new FermeResponseDto();
        fermeResponseDto2.setId(2L);
        fermeResponseDto2.setNom("Ferme2");
        fermeResponseDto2.setLocalisation("Location2");
        fermeResponseDto2.setSuperficie(150.0);
        fermeResponseDto2.setDateCreation(LocalDate.parse("2024-02-01"));

        List<FermeResponseDto> expectedDtos = Arrays.asList(fermeResponseDto1, fermeResponseDto2);

        when(fermeRepository.findAll()).thenReturn(fermes);


        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(fermeResponseDto1).thenReturn(fermeResponseDto2);

        List<FermeResponseDto> result = fermeService.getAllFermes();

        assertEquals(2, result.size());
        verify(fermeMapper, times(2)).toDto(any(Ferme.class));
    }

}

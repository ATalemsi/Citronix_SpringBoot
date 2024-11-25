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
        FermeRequestDto requestDto = FermeRequestDto.builder()
                .nom("Ferme A")
                .localisation("Localisation A")
                .superficie(100.5)
                .build();

        Ferme ferme = Ferme.builder().build();
        Ferme savedFerme = Ferme.builder().build();
        FermeResponseDto responseDto = FermeResponseDto.builder().build();

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
        Ferme ferme = Ferme.builder().id(id).build();
        FermeResponseDto responseDto = FermeResponseDto.builder().id(id).build();

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
        Ferme ferme = Ferme.builder().id(id).build();

        when(fermeRepository.findById(id)).thenReturn(Optional.of(ferme));

        fermeService.deleteFerme(id);

        verify(fermeRepository, times(1)).findById(id);
        verify(fermeRepository, times(1)).delete(ferme);
    }

    @Test
    void testGetAllFermes() {
        Ferme ferme1 = Ferme.builder()
                .id(1L)
                .nom("Ferme1")
                .localisation("Location1")
                .superficie(100.0)
                .dateCreation(LocalDate.parse("2024-01-01"))
                .build();

        Ferme ferme2 = Ferme.builder()
                .id(2L)
                .nom("Ferme2")
                .localisation("Location2")
                .superficie(150.0)
                .dateCreation(LocalDate.parse("2024-02-01"))
                .build();

        List<Ferme> fermes = Arrays.asList(ferme1, ferme2);

        FermeResponseDto fermeResponseDto1 = FermeResponseDto.builder()
                .id(1L)
                .nom("Ferme1")
                .localisation("Location1")
                .superficie(100.0)
                .dateCreation(LocalDate.parse("2024-01-01"))
                .build();

        FermeResponseDto fermeResponseDto2 = FermeResponseDto.builder()
                .id(2L)
                .nom("Ferme2")
                .localisation("Location2")
                .superficie(150.0)
                .dateCreation(LocalDate.parse("2024-02-01"))
                .build();

        List<FermeResponseDto> expectedDtos = Arrays.asList(fermeResponseDto1, fermeResponseDto2);

        when(fermeRepository.findAll()).thenReturn(fermes);
        when(fermeMapper.toDto(any(Ferme.class))).thenReturn(fermeResponseDto1).thenReturn(fermeResponseDto2);

        List<FermeResponseDto> result = fermeService.getAllFermes();

        assertEquals(2, result.size());
        verify(fermeMapper, times(2)).toDto(any(Ferme.class));
    }

}

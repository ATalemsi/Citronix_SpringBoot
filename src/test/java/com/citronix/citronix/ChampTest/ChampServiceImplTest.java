package com.citronix.citronix.ChampTest;
import com.citronix.citronix.dto.Request.ChampRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Ferme;
import com.citronix.citronix.mapper.ChampMapper;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.repository.FermeRepository;
import com.citronix.citronix.service.Implementation.ChampServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ChampServiceImplTest {

    @Mock
    private ChampRepository champRepository;

    @Mock
    private FermeRepository fermeRepository;

    @Mock
    private ChampMapper champMapper;

    @InjectMocks
    private ChampServiceImpl champService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddChamp_Success() {

        Long fermeId = 1L;
        ChampRequestDto champRequestDto = ChampRequestDto.builder()
                .fermeId(fermeId)
                .superficie(10.0)
                .build();

        Ferme ferme = Ferme.builder()
                .id(fermeId)
                .superficie(100.0)
                .champs(new ArrayList<>())
                .build();

        Champ champ = Champ.builder()
                .superficie(champRequestDto.getSuperficie())
                .ferme(ferme)
                .build();

        Champ savedChamp = Champ.builder()
                .id(1L)
                .build();

        when(fermeRepository.findById(fermeId)).thenReturn(Optional.of(ferme));
        when(champMapper.toEntity(champRequestDto)).thenReturn(champ);
        when(champRepository.save(champ)).thenReturn(savedChamp);

        ChampResponseDto responseDto = ChampResponseDto.builder()
                .id(1L)
                .build();
        when(champMapper.toDto(savedChamp)).thenReturn(responseDto);

        ChampResponseDto result = champService.addChamp(champRequestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(fermeRepository, times(1)).findById(fermeId);
        verify(champRepository, times(1)).save(champ);
        verify(champMapper, times(1)).toDto(savedChamp);
    }

    @Test
    void testAddChamp_FermeNotFound() {
        Long fermeId = 1L;
        ChampRequestDto champRequestDto = ChampRequestDto.builder()
                .fermeId(fermeId)
                .build();

        when(fermeRepository.findById(fermeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> champService.addChamp(champRequestDto));
        assertEquals("Ferme not found", exception.getMessage());
        verify(fermeRepository, times(1)).findById(fermeId);
    }

    @Test
    void testDeleteChamp_NotFound() {
        Long champId = 1L;
        when(champRepository.findById(champId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> champService.deleteChamp(champId));
        assertEquals("Champ not found", exception.getMessage());
        verify(champRepository, times(1)).findById(champId);
    }

    @Test
    void testGetAllChamps() {
        List<Champ> champs = List.of(
                Champ.builder().build(),
                Champ.builder().build()
        );

        when(champRepository.findAll()).thenReturn(champs);

        List<ChampResponseDto> responseDtos = List.of(
                ChampResponseDto.builder().build(),
                ChampResponseDto.builder().build()
        );
        when(champMapper.toDtoList(champs)).thenReturn(responseDtos);

        List<ChampResponseDto> result = champService.getAllChamps();

        assertEquals(2, result.size());
        verify(champRepository, times(1)).findAll();
        verify(champMapper, times(1)).toDtoList(champs);
    }

    @Test
    void testDeleteChamp_Success() {
        Long champId = 1L;
        Champ champ = Champ.builder()
                .id(champId)
                .build();

        when(champRepository.findById(champId)).thenReturn(Optional.of(champ));

        champService.deleteChamp(champId);

        verify(champRepository, times(1)).findById(champId);
        verify(champRepository, times(1)).delete(champ);
    }
}

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

@SpringBootTest
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
        ChampRequestDto requestDto = new ChampRequestDto();
        requestDto.setSuperficie(50.0);
        requestDto.setFermeId(1L);

        Ferme ferme = new Ferme();
        ferme.setId(1L);
        ferme.setSuperficie(200.0);

        Champ champ = new Champ();
        champ.setSuperficie(50.0);
        champ.setFerme(ferme);

        Champ savedChamp = new Champ();
        savedChamp.setId(1L);
        savedChamp.setSuperficie(50.0);
        savedChamp.setFerme(ferme);

        when(fermeRepository.findById(1L)).thenReturn(Optional.of(ferme));
        when(champMapper.toEntity(requestDto)).thenReturn(champ);
        when(champRepository.save(champ)).thenReturn(savedChamp);
        when(champMapper.toDto(savedChamp)).thenReturn(new ChampResponseDto());

        ChampResponseDto responseDto = champService.addChamp(requestDto);

        assertNotNull(responseDto);
        verify(fermeRepository, times(1)).findById(1L);
        verify(champRepository, times(1)).save(champ);
    }
    @Test
    void testAddChamp_FermeNotFound() {

        ChampRequestDto requestDto = new ChampRequestDto();
        requestDto.setFermeId(1L);

        when(fermeRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> champService.addChamp(requestDto));
        assertEquals("Ferme not found", exception.getMessage());
        verify(fermeRepository, times(1)).findById(1L);
        verifyNoInteractions(champMapper, champRepository);
    }

    @Test
    void testGetAllChamps() {
        // Arrange
        List<Champ> champs = new ArrayList<>();
        champs.add(new Champ());
        champs.add(new Champ());

        List<ChampResponseDto> champResponseDtos = new ArrayList<>();
        champResponseDtos.add(new ChampResponseDto());
        champResponseDtos.add(new ChampResponseDto());

        when(champRepository.findAll()).thenReturn(champs);
        when(champMapper.toDtoList(champs)).thenReturn(champResponseDtos);


        List<ChampResponseDto> result = champService.getAllChamps();


        assertEquals(2, result.size());
        verify(champRepository, times(1)).findAll();
        verify(champMapper, times(1)).toDtoList(champs);
    }

    @Test
    void testGetChampById_Success() {

        Long champId = 1L;
        Champ champ = new Champ();
        champ.setId(champId);

        ChampResponseDto champResponseDto = new ChampResponseDto();
        champResponseDto.setId(champId);

        when(champRepository.findById(champId)).thenReturn(Optional.of(champ));
        when(champMapper.toDto(champ)).thenReturn(champResponseDto);


        ChampResponseDto result = champService.getChampById(champId);


        assertNotNull(result);
        assertEquals(champId, result.getId());
        verify(champRepository, times(1)).findById(champId);
        verify(champMapper, times(1)).toDto(champ);
    }

    @Test
    void testDeleteChamp_Success() {

        Long champId = 1L;
        Champ champ = new Champ();
        champ.setId(champId);

        when(champRepository.findById(champId)).thenReturn(Optional.of(champ));


        champService.deleteChamp(champId);


        verify(champRepository, times(1)).findById(champId);
        verify(champRepository, times(1)).delete(champ);
    }

    @Test
    void testDeleteChamp_NotFound() {

        Long champId = 1L;
        when(champRepository.findById(champId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> champService.deleteChamp(champId));
        assertEquals("Champ not found", exception.getMessage());
        verify(champRepository, times(1)).findById(champId);
        verify(champRepository, times(0)).delete(any());
    }

}

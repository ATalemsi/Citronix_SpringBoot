package com.citronix.citronix.recolteTest;

import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.entity.Enum.Saison;
import com.citronix.citronix.entity.Recolte;
import com.citronix.citronix.exception.GlobalExceptionHandler;
import com.citronix.citronix.mapper.ChampMapper;
import com.citronix.citronix.mapper.RecolteMapper;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.repository.RecolteRepository;
import com.citronix.citronix.service.Implementation.RecolteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class RecolteServiceImplTest {

    @InjectMocks
    private RecolteServiceImpl recolteService;

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private ChampRepository champRepository;

    @Mock
    private RecolteMapper recolteMapper;

    @Mock
    private ChampMapper champMapper;

    private Recolte mockRecolte;
    private Champ mockChamp;
    private RecolteRequestDto validRequest;
    private RecolteResponseDto expectedResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockRecolte = Recolte.builder()
                .id(1L)
                .saison(Saison.ETE)
                .dateRecolte(LocalDate.now().minusDays(1))
                .recoltedetailsList(new ArrayList<>())
                .build();

        validRequest = RecolteRequestDto.builder()
                .saison(Saison.AUTOMME)
                .dateRecolte(LocalDate.now())
                .build();

        expectedResponse = RecolteResponseDto.builder()
                .saison(Saison.AUTOMME)
                .dateRecolte(LocalDate.now())
                .build();
    }

    @Test
    void testAddRecolte_Success() {
        when(recolteMapper.toEntity(validRequest)).thenReturn(mockRecolte);
        when(recolteRepository.save(mockRecolte)).thenReturn(mockRecolte);
        when(recolteMapper.toDto(mockRecolte)).thenReturn(expectedResponse);

        RecolteResponseDto result = recolteService.addRecolte(validRequest);

        assertNotNull(result);
        assertEquals(Saison.AUTOMME, result.getSaison());
        assertEquals(validRequest.getDateRecolte(), result.getDateRecolte());

        verify(recolteMapper).toEntity(validRequest);
        verify(recolteRepository).save(mockRecolte);
        verify(recolteMapper).toDto(mockRecolte);
    }

    @Test
    void testUpdateRecolte_Success() {
        when(recolteRepository.findById(mockRecolte.getId())).thenReturn(Optional.of(mockRecolte));
        when(recolteRepository.save(mockRecolte)).thenReturn(mockRecolte);
        when(recolteMapper.toDto(mockRecolte)).thenReturn(expectedResponse);

        RecolteResponseDto result = recolteService.updateRecolte(mockRecolte.getId(), validRequest);

        assertNotNull(result);
        assertEquals(Saison.AUTOMME, result.getSaison());
        assertEquals(validRequest.getDateRecolte(), result.getDateRecolte());

        verify(recolteRepository).findById(mockRecolte.getId());
        verify(recolteRepository).save(mockRecolte);
        verify(recolteMapper).toDto(mockRecolte);
    }

    @Test
    void testUpdateRecolte_NotFound() {
        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> recolteService.updateRecolte(1L, validRequest));
    }

    @Test
    void testDeleteRecolte_Success() {
        when(recolteRepository.existsById(mockRecolte.getId())).thenReturn(true);
        doNothing().when(recolteRepository).deleteById(mockRecolte.getId());

        recolteService.deleteRecolte(mockRecolte.getId());

        verify(recolteRepository).existsById(mockRecolte.getId());
        verify(recolteRepository).deleteById(mockRecolte.getId());
    }

    @Test
    void testDeleteRecolte_NotFound() {
        when(recolteRepository.existsById(mockRecolte.getId())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> recolteService.deleteRecolte(mockRecolte.getId()));
        assertEquals("Recolte not found", exception.getMessage());

        verify(recolteRepository).existsById(mockRecolte.getId());
        verify(recolteRepository, never()).deleteById(mockRecolte.getId());
    }
}

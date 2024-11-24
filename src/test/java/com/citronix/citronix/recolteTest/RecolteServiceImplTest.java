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

    private RecolteRequestDto validRequest;
    private Recolte mockRecolte;
    private RecolteResponseDto expectedResponse;
    private ChampResponseDto mockChampResponseDto;

    private Champ mockChamp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockChamp = new Champ();
        mockChamp.setId(1L);
        mockChamp.setSuperficie(1.0);

        mockChamp = new Champ();
        mockChamp.setId(1L);
        mockChamp.setSuperficie(1.0);

        mockChampResponseDto = new ChampResponseDto();
        mockChampResponseDto.setId(1L);
        mockChampResponseDto.setSuperficie(1.0);


        mockRecolte = new Recolte();
        mockRecolte.setId(1L);
        mockRecolte.setSaison(Saison.ETE);
        mockRecolte.setDateRecolte(LocalDate.now().minusDays(1));
        mockRecolte.setChamp(mockChamp);
        mockRecolte.setRecoltedetailsList(new ArrayList<>());


        validRequest = new RecolteRequestDto();
        validRequest.setChampId(1L);
        validRequest.setSaison(Saison.AUTOMME);
        validRequest.setDateRecolte(LocalDate.now());

        expectedResponse = new RecolteResponseDto();
        expectedResponse.setSaison(Saison.AUTOMME);
        expectedResponse.setDateRecolte(LocalDate.now());
        expectedResponse.setChamp(mockChampResponseDto);


    }

    @Test
    void testAddRecolte_Success() {

        RecolteRequestDto requestDto = new RecolteRequestDto();
        requestDto.setSaison(Saison.ETE);
        requestDto.setDateRecolte(LocalDate.now().minusDays(1));
        requestDto.setChampId(mockChamp.getId());

        when(champRepository.findById(mockChamp.getId())).thenReturn(Optional.of(mockChamp));
        when(recolteMapper.toEntity(requestDto)).thenReturn(mockRecolte);
        when(recolteRepository.save(mockRecolte)).thenReturn(mockRecolte);
        when(recolteMapper.toDto(mockRecolte)).thenReturn(new RecolteResponseDto());

        RecolteResponseDto result = recolteService.addRecolte(requestDto);

        assertNotNull(result, "Result should not be null");
        verify(champRepository, times(1)).findById(mockChamp.getId());
        verify(recolteMapper, times(1)).toEntity(requestDto);
        verify(recolteRepository, times(1)).save(mockRecolte);
    }

    @Test
    void testUpdateRecolte_Success() {

        Champ mockChamp = new Champ();
        mockChamp.setId(1L);
        mockChamp.setSuperficie(1.0);

        Recolte mockRecolte = new Recolte();
        mockRecolte.setId(1L);
        mockRecolte.setSaison(Saison.ETE);
        mockRecolte.setDateRecolte(LocalDate.now().minusDays(1));
        mockRecolte.setChamp(mockChamp);
        mockRecolte.setRecoltedetailsList(new ArrayList<>());

        RecolteRequestDto validRequest = new RecolteRequestDto();
        validRequest.setChampId(1L);
        validRequest.setSaison(Saison.AUTOMME);
        validRequest.setDateRecolte(LocalDate.now());

        RecolteResponseDto expectedResponse = new RecolteResponseDto();
        expectedResponse.setSaison(Saison.AUTOMME);
        expectedResponse.setDateRecolte(LocalDate.now());

        when(recolteRepository.findById(mockRecolte.getId())).thenReturn(Optional.of(mockRecolte));
        when(champRepository.findById(mockChamp.getId())).thenReturn(Optional.of(mockChamp));
        when(recolteRepository.save(mockRecolte)).thenReturn(mockRecolte);

        ChampResponseDto champResponseDto = new ChampResponseDto();
        champResponseDto.setId(1L);
        champResponseDto.setSuperficie(1.0);
        when(champMapper.toDto(mockChamp)).thenReturn(champResponseDto);

        when(recolteMapper.toDto(mockRecolte)).thenReturn(expectedResponse);

        RecolteResponseDto result = recolteService.updateRecolte(mockRecolte.getId(), validRequest);

        assertNotNull(result, "Result should not be null");
        assertEquals(Saison.AUTOMME, result.getSaison(), "Saison should be AUTOMNE");
        assertEquals(validRequest.getDateRecolte(), result.getDateRecolte(), "DateRecolte should match");
        assertEquals(1L, result.getChamp().getId(), "Champ ID should match");
        assertEquals(1.0, result.getChamp().getSuperficie(), "Champ Superficie should match");

        // Verify repository calls
        verify(recolteRepository, times(1)).findById(mockRecolte.getId());
        verify(champRepository, times(1)).findById(mockChamp.getId());
        verify(recolteMapper, times(1)).toDto(mockRecolte);
        verify(champMapper, times(1)).toDto(mockChamp);  // Ensure that this interaction happens
    }




    @Test
    void testUpdateRecolte_NotFound() {
        // Arrange: Mock repository methods
        when(recolteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert: Verify exception is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            recolteService.updateRecolte(1L, validRequest);
        });
    }

    @Test
    void testDeleteRecolte_Success() {

        when(recolteRepository.existsById(mockRecolte.getId())).thenReturn(true);
        doNothing().when(recolteRepository).deleteById(mockRecolte.getId());

        recolteService.deleteRecolte(mockRecolte.getId());

        verify(recolteRepository, times(1)).existsById(mockRecolte.getId());
        verify(recolteRepository, times(1)).deleteById(mockRecolte.getId());
    }


    @Test
    void testDeleteRecolte_NotFound() {

        when(recolteRepository.existsById(mockRecolte.getId())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            recolteService.deleteRecolte(mockRecolte.getId());
        });


        assertEquals("Recolte not found", exception.getMessage());


        verify(recolteRepository, times(1)).existsById(mockRecolte.getId());
        verify(recolteRepository, never()).deleteById(mockRecolte.getId());
    }



}

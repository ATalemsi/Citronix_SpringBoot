package com.citronix.citronix.arbreTest;

import com.citronix.citronix.dto.Request.ArbreRequestDto;
import com.citronix.citronix.dto.Response.ArbreResponseDto;
import com.citronix.citronix.entity.Arbre;
import com.citronix.citronix.entity.Champ;
import com.citronix.citronix.mapper.ArbreMapper;
import com.citronix.citronix.repository.ArbreRepository;
import com.citronix.citronix.repository.ChampRepository;
import com.citronix.citronix.service.Implementation.ArbreServiceImpl;
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
public class ArbreServiceImplTest {


    @InjectMocks
    private ArbreServiceImpl arbreService;

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampRepository champRepository;

    @Mock
    private ArbreMapper arbreMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddArbre_Success() {
        // Arrange
        Long champId = 1L;
        Long arbreId = 1L;
        LocalDate datePlantation = LocalDate.of(2023, 4, 15);

        ArbreRequestDto arbreRequestDto = ArbreRequestDto.builder()
                .champId(champId)
                .datePlantation(datePlantation)
                .build();

        Champ champ = Champ.builder()
                .id(champId)
                .superficie(1.0)
                .arbreList(new ArrayList<>()) // Initialize arbreList
                .build();

        Arbre arbre = Arbre.builder()
                .datePlantation(datePlantation)
                .champ(champ)
                .build();

        Arbre savedArbre = Arbre.builder()
                .id(arbreId)
                .datePlantation(datePlantation)
                .champ(champ)
                .build();

        Arbre updatedArbre = Arbre.builder()
                .id(arbreId)
                .datePlantation(datePlantation)
                .champ(champ)
                .agePlantation(1)
                .productivite("2.5 kg / saison")
                .build();

        ArbreResponseDto responseDto = ArbreResponseDto.builder()
                .id(arbreId)
                .build();

        when(champRepository.findById(champId)).thenReturn(Optional.of(champ));
        when(arbreMapper.toEntity(arbreRequestDto)).thenReturn(arbre);
        when(arbreRepository.save(arbre)).thenReturn(savedArbre);
        when(arbreRepository.findById(arbreId)).thenReturn(Optional.of(savedArbre));
        when(arbreRepository.save(savedArbre)).thenReturn(updatedArbre);
        when(arbreMapper.toDto(updatedArbre)).thenReturn(responseDto);

        // Act
        ArbreResponseDto result = arbreService.addArbre(arbreRequestDto);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(arbreId, result.getId(), "The Arbre ID should match the expected value");
        verify(champRepository, times(1)).findById(champId);
        verify(arbreMapper, times(1)).toEntity(arbreRequestDto);
        verify(arbreRepository, times(2)).save(any(Arbre.class));
        verify(arbreRepository, times(1)).findById(arbreId);
        verify(arbreMapper, times(1)).toDto(updatedArbre);
    }

    @Test
    void testAddArbre_Failure_InvalidPlantingPeriod() {
        // Arrange
        Long champId = 1L;
        LocalDate invalidDate = LocalDate.of(2023, 6, 15);

        ArbreRequestDto arbreRequestDto = ArbreRequestDto.builder()
                .champId(champId)
                .datePlantation(invalidDate)
                .build();

        Champ champ = Champ.builder()
                .id(champId)
                .build();

        when(champRepository.findById(champId)).thenReturn(Optional.of(champ));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                arbreService.addArbre(arbreRequestDto));
        assertEquals("Les arbres ne peuvent être plantés qu'entre mars et mai.", exception.getMessage());
        verify(champRepository, times(1)).findById(champId);
        verify(arbreRepository, never()).save(any());
    }

    @Test
    void testAddArbre_Failure_ExceedsDensity() {
        // Arrange
        Long champId = 1L;
        LocalDate datePlantation = LocalDate.of(2023, 4, 15);

        ArbreRequestDto arbreRequestDto = ArbreRequestDto.builder()
                .champId(champId)
                .datePlantation(datePlantation)
                .build();

        List<Arbre> arbreList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arbreList.add(Arbre.builder().build());
        }

        Champ champ = Champ.builder()
                .id(champId)
                .superficie(0.1)
                .arbreList(arbreList) // Set arbreList with 10 trees
                .build();

        when(champRepository.findById(champId)).thenReturn(Optional.of(champ));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                arbreService.addArbre(arbreRequestDto));
        assertEquals("Le nombre d'arbres dépasse la densité maximale autorisée de 100 arbres par hectare.", exception.getMessage());
        verify(champRepository, times(1)).findById(champId);
        verify(arbreRepository, never()).save(any());
    }

}

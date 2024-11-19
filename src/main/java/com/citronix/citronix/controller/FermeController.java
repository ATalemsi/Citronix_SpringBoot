package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.FermeRequestDto;
import com.citronix.citronix.dto.Response.FermeResponseDto;
import com.citronix.citronix.entity.Ferme;
import com.citronix.citronix.mapper.FermeMapper;
import com.citronix.citronix.service.Interface.FermeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fermes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FermeController {
    private final FermeService fermeService;
    private final FermeMapper fermeMapper;

    @PostMapping("/add")
    public ResponseEntity<FermeResponseDto> createFerme(@Validated @RequestBody FermeRequestDto dto) {
        return new ResponseEntity<>(fermeService.createFerme(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<FermeResponseDto> partialUpdateFerme(
            @PathVariable Long id,
            @Validated @RequestBody FermeRequestDto requestDto) {
        Ferme updatedFerme = fermeService.updateFerme(id, requestDto);
        return ResponseEntity.ok(fermeMapper.toDto(updatedFerme));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FermeResponseDto> getFermeById(@PathVariable Long id) {
        return ResponseEntity.ok(fermeService.getFermeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FermeResponseDto>> getAllFermes() {
        return ResponseEntity.ok(fermeService.getAllFermes());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFerme(@PathVariable Long id) {
        fermeService.deleteFerme(id);
        return ResponseEntity.noContent().build();
    }

}

package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.dto.UpdateGroup;
import com.citronix.citronix.dto.updateDto.UpdateVenteDto;
import com.citronix.citronix.service.Interface.VenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VenteController {

    private final VenteService venteService;


    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addVente(@Valid @RequestBody VenteRequestDto venteRequestDto) {
        VenteResponseDto response = venteService.addVente(venteRequestDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Vente created successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String , Object>> getAllVentes() {
        List<VenteResponseDto> response = venteService.getAllVentes();

        Map<String , Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Ventes retrieved successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String , Object>> getVenteById(@PathVariable Long id) {
        VenteResponseDto response = venteService.getVenteById(id);

        Map<String , Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Ventes retrieved successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateVente(
            @PathVariable Long id,
            @Validated(UpdateGroup.class) @RequestBody UpdateVenteDto updateVenteDto) {

        VenteResponseDto response = venteService.updateVente(id, updateVenteDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Vente updated successfully");
        responseBody.put("data", response);

        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteVente(@PathVariable Long id) {
        venteService.deleteVente(id);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Vente deleted successfully");
        return ResponseEntity.ok(responseBody);
    }



}

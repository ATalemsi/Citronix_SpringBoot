package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.VenteRequestDto;
import com.citronix.citronix.dto.Response.VenteResponseDto;
import com.citronix.citronix.service.Interface.VenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
}

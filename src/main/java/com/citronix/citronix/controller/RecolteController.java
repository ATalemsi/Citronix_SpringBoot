package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.service.Interface.RecolteService;
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
@RequestMapping("/api/recoltes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecolteController {

    private final RecolteService recolteService;


    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addRecolte(@Valid @RequestBody RecolteRequestDto recolteRequestDto) {
        RecolteResponseDto response = recolteService.addRecolte(recolteRequestDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Récolte created successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}

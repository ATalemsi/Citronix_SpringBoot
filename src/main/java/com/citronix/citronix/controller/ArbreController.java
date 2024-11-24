package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.ArbreRequestDto;
import com.citronix.citronix.dto.Response.ArbreResponseDto;
import com.citronix.citronix.service.Interface.ArbreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/arbres")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArbreController {

    private final ArbreService arbreService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addArbre(@Valid @RequestBody ArbreRequestDto arbreRequestDto) {
        ArbreResponseDto response = arbreService.addArbre(arbreRequestDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Arbre created successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }


}

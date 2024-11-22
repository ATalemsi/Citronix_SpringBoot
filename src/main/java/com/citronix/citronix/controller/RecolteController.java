package com.citronix.citronix.controller;

import com.citronix.citronix.dto.CreateGroup;
import com.citronix.citronix.dto.Request.RecolteRequestDto;
import com.citronix.citronix.dto.Response.RecolteResponseDto;
import com.citronix.citronix.dto.UpdateGroup;
import com.citronix.citronix.service.Interface.RecolteService;
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
@RequestMapping("/api/recoltes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecolteController {

    private final RecolteService recolteService;


    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addRecolte(@Validated(CreateGroup.class) @RequestBody RecolteRequestDto recolteRequestDto) {
        RecolteResponseDto response = recolteService.addRecolte(recolteRequestDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Récolte created successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateRecolte(
            @PathVariable Long id,
            @Validated(UpdateGroup.class) @RequestBody RecolteRequestDto recolteRequestDto) {

        RecolteResponseDto response = recolteService.updateRecolte(id, recolteRequestDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Récolte updated successfully");
        responseBody.put("data", response);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRecolteById(@PathVariable Long id) {
        RecolteResponseDto response = recolteService.getRecolteById(id);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Récolte retrieved successfully");
        responseBody.put("data", response);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllRecoltes() {
        List<RecolteResponseDto> response = recolteService.getAllRecoltes();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Récoltes retrieved successfully");
        responseBody.put("data", response);

        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteRecolte(@PathVariable Long id) {
        recolteService.deleteRecolte(id);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Récolte deleted successfully");

        return ResponseEntity.ok(responseBody);
    }
}

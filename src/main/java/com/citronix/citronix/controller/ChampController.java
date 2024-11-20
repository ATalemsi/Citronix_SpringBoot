package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.ChampRequestDto;
import com.citronix.citronix.dto.Response.ChampResponseDto;
import com.citronix.citronix.dto.UpdateGroup;
import com.citronix.citronix.service.Interface.ChampService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@RequestMapping("/api/champs")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChampController {
    private final ChampService champService;


    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addChamp(@Valid @RequestBody ChampRequestDto champRequestDto) {
        ChampResponseDto response = champService.addChamp(champRequestDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Champ created successfully");
        responseBody.put("data", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateChamp(@Validated(UpdateGroup.class) @RequestBody ChampRequestDto champRequestDto, @PathVariable Long id) {
        ChampResponseDto updatedChamp = champService.updateChamp(id, champRequestDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CREATED.value());
        responseBody.put("message", "Champ update successfully");
        responseBody.put("data", updatedChamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteChamp(@PathVariable Long id) {
        champService.deleteChamp(id);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Champ deleted successfully");
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllChamps() {
        List<ChampResponseDto> response = champService.getAllChamps();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Champs retrieved successfully");
        responseBody.put("data", response);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getChampById(@PathVariable Long id) {
        ChampResponseDto response = champService.getChampById(id);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Champ retrieved successfully");
        responseBody.put("data", response);
        return ResponseEntity.ok(responseBody);
    }

}

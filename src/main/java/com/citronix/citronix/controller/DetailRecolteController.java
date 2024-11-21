package com.citronix.citronix.controller;

import com.citronix.citronix.dto.Request.DetailRecolteRequestDto;
import com.citronix.citronix.dto.Response.DetailRecolteResponseDto;
import com.citronix.citronix.entity.Recolte;
import com.citronix.citronix.service.Interface.DetailRecolteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/details-recolte")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DetailRecolteController {

    private final DetailRecolteService detailRecolteService;


    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addDetailsRecolte(@Valid @RequestBody DetailRecolteRequestDto detailRecolteRequestDto) {
        DetailRecolteResponseDto response =detailRecolteService.addDetailRecolte(detailRecolteRequestDto);

        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("status" , HttpStatus.CREATED.value());
        responseBody.put("message", "Detail Recolte created successfully");
        responseBody.put("detailRecolte",response);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllDetailsByRecolteId() {
        List<DetailRecolteResponseDto> details = detailRecolteService.getAllDetails();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Details fetched successfully");
        responseBody.put("details", details);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDetailRecolteById(@PathVariable Long id) {
        DetailRecolteResponseDto detail = detailRecolteService.getDetailRecolteById(id);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Detail fetched successfully");
        responseBody.put("detail", detail);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateDetailRecolte(@PathVariable Long id, @Valid @RequestBody DetailRecolteRequestDto detailRecolteRequestDto) {
        DetailRecolteResponseDto updatedDetail = detailRecolteService.updateDetailRecolte(id, detailRecolteRequestDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "Detail Recolte updated successfully");
        responseBody.put("detailRecolte", updatedDetail);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteDetailRecolte(@PathVariable Long id) {
        detailRecolteService.deleteDetailRecolte(id);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.NO_CONTENT.value());
        responseBody.put("message", "Detail Recolte deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBody);
    }

    @PostMapping("/recolte/{recolteId}/calculate-total")
    public ResponseEntity<Map<String, Object>> calculateAndUpdateTotalQuantity(@PathVariable Long recolteId) {
        double totalQuantity = detailRecolteService.calculateAndUpdateTotalQuantity(recolteId);

        Map<String, Object> response = new HashMap<>();
        response.put("recolteId", recolteId);
        response.put("totalQuantity", totalQuantity);

        return ResponseEntity.ok(response);
    }

}

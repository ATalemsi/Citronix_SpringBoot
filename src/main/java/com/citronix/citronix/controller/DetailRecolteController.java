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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
}

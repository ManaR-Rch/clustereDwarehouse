package com.example.demo.controller;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.service.DealService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping
    public ResponseEntity<DealResponseDto> createDeal(@Valid @RequestBody DealRequestDto request) {
        DealResponseDto response = dealService.createDeal(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

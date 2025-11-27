package com.example.demo.service;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;

public interface DealService {

    DealResponseDto createDeal(DealRequestDto request);

}

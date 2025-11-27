package com.example.demo.mapper;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.model.Deal;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {

    public Deal toEntity(DealRequestDto dto) {
        if (dto == null) return null;

        return Deal.builder()
                .id(dto.getId())
                .fromCurrency(dto.getFromCurrency())
                .toCurrency(dto.getToCurrency())
                .timestamp(dto.getTimestamp())
                .amount(dto.getAmount())
                .build();
     
    }

    public DealResponseDto toResponse(Deal entity) {
        if (entity == null) return null;

        return DealResponseDto.builder()
                .id(entity.getId())
                .fromCurrency(entity.getFromCurrency())
                .toCurrency(entity.getToCurrency())
                .timestamp(entity.getTimestamp())
                .amount(entity.getAmount())
                .build();
    }
}

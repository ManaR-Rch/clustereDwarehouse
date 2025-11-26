package com.example.demo.service;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.model.Deal;
import com.example.demo.repository.DealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealService {

    private final DealRepository repository;

    public DealService(DealRepository repository) {
        this.repository = repository;
    }

    // Manual mapper: Request DTO -> Entity
    private Deal toEntity(DealRequestDto dto) {
        Deal d = new Deal();
        d.setId(dto.getId());
        d.setFromCurrency(dto.getFromCurrency());
        d.setToCurrency(dto.getToCurrency());
        d.setTimestamp(dto.getTimestamp());
        d.setAmount(dto.getAmount());
        return d;
    }

    private DealResponseDto toResponse(Deal entity) {
        return new DealResponseDto(
                entity.getId(),
                entity.getFromCurrency(),
                entity.getToCurrency(),
                entity.getTimestamp(),
                entity.getAmount()
        );
    }

    public DealResponseDto createDeal(DealRequestDto request) {
        Deal saved = repository.save(toEntity(request));
        return toResponse(saved);
    }

    public List<DealResponseDto> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

}

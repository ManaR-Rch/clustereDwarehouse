package com.example.demo.service;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.model.Deal;
import com.example.demo.repository.DealRepository;
import org.springframework.stereotype.Service;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository repository;

    public DealServiceImpl(DealRepository repository) {
        this.repository = repository;
    }

    @Override
    public DealResponseDto createDeal(DealRequestDto request) {
        if (request.getFromCurrency() != null && request.getFromCurrency().equals(request.getToCurrency())) {
            throw new IllegalArgumentException("fromCurrency and toCurrency must be different");
        }

        if (repository.existsById(request.getId())) {
            throw new IllegalArgumentException("Deal with id " + request.getId() + " already exists");
        }

        Deal entity = toEntity(request);
        Deal saved = repository.save(entity);
        return toResponse(saved);
    }

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

}

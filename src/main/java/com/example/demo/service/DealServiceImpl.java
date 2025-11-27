package com.example.demo.service;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.mapper.DealMapper;
import com.example.demo.model.Deal;
import com.example.demo.repository.DealRepository;
import org.springframework.stereotype.Service;

@Service
public class DealServiceImpl implements DealService {

    private final DealRepository repository;
    private final DealMapper mapper;

    public DealServiceImpl(DealRepository repository, DealMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DealResponseDto createDeal(DealRequestDto request) {

        if (request.getFromCurrency() != null &&
            request.getFromCurrency().equals(request.getToCurrency())) {
            throw new IllegalArgumentException("fromCurrency and toCurrency must be different");
        }

        if (repository.existsById(request.getId())) {
            throw new IllegalArgumentException("Deal with id " + request.getId() + " already exists");
        }

        Deal entity = mapper.toEntity(request);

        
        Deal saved = repository.save(entity);

       
        return mapper.toResponse(saved);
    }
}

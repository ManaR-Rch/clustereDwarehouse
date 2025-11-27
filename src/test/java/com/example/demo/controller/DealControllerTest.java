package com.example.demo.controller;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.service.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealControllerTest {

    @Mock
    private DealService dealService;

    @InjectMocks
    private DealController controller;

    @Test
    void createDeal_returnsCreated() {
        DealRequestDto request = new DealRequestDto("d1", "EUR", "USD", LocalDateTime.now(), new BigDecimal("10.0"));
        DealResponseDto resp = new DealResponseDto("d1", "EUR", "USD", request.getTimestamp(), request.getAmount());

        when(dealService.createDeal(request)).thenReturn(resp);

        ResponseEntity<DealResponseDto> result = controller.createDeal(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(resp);
    }

}

package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealResponseDto {

    private String id;
    private String fromCurrency;
    private String toCurrency;
    private LocalDateTime timestamp;
    private BigDecimal amount;

}

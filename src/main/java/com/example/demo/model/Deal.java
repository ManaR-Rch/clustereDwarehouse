package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "deals")
public class Deal {

    @Id
    private String id;

    @Column(name = "from_currency", nullable = false)
    private String fromCurrency;

    @Column(name = "to_currency", nullable = false)
    private String toCurrency;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

}

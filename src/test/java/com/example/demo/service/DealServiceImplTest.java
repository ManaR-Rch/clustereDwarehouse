package com.example.demo.service;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.model.Deal;
import com.example.demo.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository repository;

    @InjectMocks
    private DealServiceImpl service;

    private DealRequestDto validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new DealRequestDto(
                "d1",
                "EUR",
                "USD",
                LocalDateTime.now(),
                new BigDecimal("100.00")
        );
    }

    @Test
    void createDeal_success() {
        when(repository.existsById("d1")).thenReturn(false);

        ArgumentCaptor<Deal> captor = ArgumentCaptor.forClass(Deal.class);
        Deal returned = new Deal("d1", "EUR", "USD", validRequest.getTimestamp(), validRequest.getAmount());
        when(repository.save(any(Deal.class))).thenReturn(returned);

        DealResponseDto response = service.createDeal(validRequest);

        verify(repository).existsById("d1");
        verify(repository).save(captor.capture());

        Deal saved = captor.getValue();
        assertThat(saved.getId()).isEqualTo("d1");
        assertThat(response.getId()).isEqualTo("d1");
        assertThat(response.getFromCurrency()).isEqualTo("EUR");
    }

    @Test
    void createDeal_sameCurrency_throws() {
        DealRequestDto req = new DealRequestDto("d2", "USD", "USD", LocalDateTime.now(), BigDecimal.ONE);
        assertThatThrownBy(() -> service.createDeal(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("fromCurrency and toCurrency must be different");

        verifyNoInteractions(repository);
    }

    @Test
    void createDeal_existingId_throws() {
        when(repository.existsById("d1")).thenReturn(true);

        assertThatThrownBy(() -> service.createDeal(validRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Deal with id d1 already exists");

        verify(repository).existsById("d1");
        verify(repository, never()).save(any());
    }

}

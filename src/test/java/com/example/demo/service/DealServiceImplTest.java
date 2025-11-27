package com.example.demo.service;

import com.example.demo.dto.DealRequestDto;
import com.example.demo.dto.DealResponseDto;
import com.example.demo.mapper.DealMapper;
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
    @Mock
    private DealMapper mapper;
  @InjectMocks
  private DealServiceImpl service;
  @Mock
  private DealRequestDto validRequest;

  @BeforeEach
  void setUp() {
    validRequest = new DealRequestDto(
        "d1",
        "EUR",
        "USD",
        LocalDateTime.now(),
        new BigDecimal("100.00"));
  }

    @Test
    void createDeal_success() {
        when(repository.existsById("d1")).thenReturn(false);

        Deal entity = new Deal("d1", "EUR", "USD", validRequest.getTimestamp(), validRequest.getAmount());
        Deal saved = new Deal("d1", "EUR", "USD", validRequest.getTimestamp(), validRequest.getAmount());

        DealResponseDto responseDto = new DealResponseDto(
                "d1", "EUR", "USD", validRequest.getTimestamp(), validRequest.getAmount()
        );

        // mock mapper
        when(mapper.toEntity(validRequest)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(responseDto);

        DealResponseDto result = service.createDeal(validRequest);

        assertThat(result.getId()).isEqualTo("d1");
        assertThat(result.getFromCurrency()).isEqualTo("EUR");

        verify(mapper).toEntity(validRequest);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
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

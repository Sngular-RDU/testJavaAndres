package com.example.prueba.tecnica.pruebaTecnica.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder(toBuilder = true)
public record Prices(
    Long id,
    Long brandId,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Long productId,
    Boolean priority,
    float price,
    Currency currency
) {
}

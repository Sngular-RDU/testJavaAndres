package com.example.prueba.tecnica.pruebaTecnica.service.impl;

import java.time.LocalDateTime;

import javax.management.InstanceNotFoundException;

import com.example.prueba.tecnica.pruebaTecnica.infrastructure.repositories.PricesRepository;
import com.example.prueba.tecnica.pruebaTecnica.model.Prices;
import com.example.prueba.tecnica.pruebaTecnica.service.PricingService;
import com.example.prueba.tecnica.pruebaTecnica.service.mappers.PricesMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PricingServiceImpl implements PricingService {

  private final PricesRepository pricesRepository;

  @Override
  public Prices findPrices(final LocalDateTime dateQuery, final Long productId, final Long brandId) throws InstanceNotFoundException {

    return PricesMapper.INSTANCE.pricesRepositoryEntityToPrices(
        pricesRepository.findPricesBetweenDates(dateQuery, productId, brandId)
                        .orElseThrow(InstanceNotFoundException::new)
    );
  }
}

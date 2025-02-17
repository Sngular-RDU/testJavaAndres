package com.example.prueba.tecnica.pruebaTecnica.api;

import java.time.LocalDateTime;

import com.example.prueba.tecnica.pruebaTecnica.api.mappers.PricesResultMapper;
import com.example.prueba.tecnica.pruebaTecnica.model.Prices;
import com.example.prueba.tecnica.pruebaTecnica.service.PricingService;
import com.sngular.apigenerator.openapi.api.FindPriceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PruebaTecnicaApi implements FindPriceApi {

  private final PricingService pricingService;

  private final ExceptionMapper exceptionMapper;

  @Override
  public ResponseEntity findPrice(String dateQuery, Long productId, Long brandId) {
    try {
      LocalDateTime dateQueryParsed = LocalDateTime.parse(dateQuery);
      Prices prices = pricingService.findPrices(dateQueryParsed, productId, brandId);
      return ResponseEntity.ok(
          PricesResultMapper
              .INSTANCE
              .pricesToPricesResultDTO(prices)
      );
    } catch (Exception e) {
      return exceptionMapper.exceptionToApiError(e);
    }
  }
}
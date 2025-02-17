package com.example.prueba.tecnica.pruebaTecnica.service;

import java.time.LocalDateTime;

import javax.management.InstanceNotFoundException;

import com.example.prueba.tecnica.pruebaTecnica.model.Prices;

public interface PricingService {

  Prices findPrices(LocalDateTime dateQuery, Long productId, Long brandId) throws InstanceNotFoundException;
}

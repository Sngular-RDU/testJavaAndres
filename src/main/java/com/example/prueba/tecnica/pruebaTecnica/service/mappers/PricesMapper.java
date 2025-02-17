package com.example.prueba.tecnica.pruebaTecnica.service.mappers;

import com.example.prueba.tecnica.pruebaTecnica.infrastructure.entities.PricesRepositoryEntity;
import com.example.prueba.tecnica.pruebaTecnica.model.Prices;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PricesMapper {

  PricesMapper INSTANCE = Mappers.getMapper(PricesMapper.class);

  PricesRepositoryEntity pricesToPricesRepositoryEntity(Prices prices);

  Prices pricesRepositoryEntityToPrices(PricesRepositoryEntity pricesRepositoryEntity);

}

package com.example.prueba.tecnica.pruebaTecnica.api.mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.example.prueba.tecnica.pruebaTecnica.model.Prices;
import com.sngular.apigenerator.openapi.api.model.PricesResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PricesResultMapper {

  PricesResultMapper INSTANCE = Mappers.getMapper(PricesResultMapper.class);

  @Mapping(target = "priceList", source = "id")
  @Mapping(
      target = "price",
      qualifiedByName = "floatToScaledBigDecimal"
  )
  PricesResultDTO pricesToPricesResultDTO(Prices prices);

  @Named("floatToScaledBigDecimal")
  default BigDecimal floatToScaledBigDecimal(Float price) {
    return price != null ?
               new BigDecimal(price.toString()).setScale(2, RoundingMode.HALF_UP) :
                                                                                      null;
  }
}

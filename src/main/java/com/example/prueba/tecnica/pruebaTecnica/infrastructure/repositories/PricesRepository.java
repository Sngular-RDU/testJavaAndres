package com.example.prueba.tecnica.pruebaTecnica.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.prueba.tecnica.pruebaTecnica.infrastructure.entities.PricesRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PricesRepository extends JpaRepository<PricesRepositoryEntity, Long> {

  @Query("SELECT p FROM PricesRepositoryEntity p " +
         "WHERE p.productId = :productId AND p.brandId = :brandId " +
         "AND :dateQuery BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC LIMIT 1")
  Optional<PricesRepositoryEntity> findPricesBetweenDates(
      @Param("dateQuery") LocalDateTime dateQuery,
      @Param("productId") Long productId,
      @Param("brandId") Long brandId);

}

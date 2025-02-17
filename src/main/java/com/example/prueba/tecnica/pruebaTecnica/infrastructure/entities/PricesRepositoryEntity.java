package com.example.prueba.tecnica.pruebaTecnica.infrastructure.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "prices")
@Data
@EqualsAndHashCode
public class PricesRepositoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long brandId;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private Long productId;

  private Boolean priority;

  private Float price;

  private String currency;

  @Override
  public String toString() {
    return "PricesRepositoryEntity{" +
           "id=" + id +
           ", brandId=" + brandId +
           ", startDate='" + startDate + '\'' +
           ", endDate='" + endDate + '\'' +
           ", productId=" + productId +
           ", priority=" + priority +
           ", price=" + price +
           ", currency='" + currency + '\'' +
           '}';
  }

}

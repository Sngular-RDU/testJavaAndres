package com.example.prueba.tecnica.pruebaTecnica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import com.example.prueba.tecnica.pruebaTecnica.infrastructure.repositories.PricesRepository;
import com.example.prueba.tecnica.pruebaTecnica.model.Currency;
import com.example.prueba.tecnica.pruebaTecnica.model.Prices;
import com.example.prueba.tecnica.pruebaTecnica.service.mappers.PricesMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class PruebaTecnicaApiTest {

  private static final Long BRAND_ID = 1L;

  private static final Long PRODUCT_ID = 35455L;

  @Autowired
  private PricesRepository pricesRepository;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    pricesRepository.save(
        PricesMapper.INSTANCE.pricesToPricesRepositoryEntity(
            Prices.builder()
                  .brandId(BRAND_ID)
                  .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                  .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                  .productId(PRODUCT_ID)
                  .priority(false)
                  .price(35.5f)
                  .currency(Currency.EUR)
                  .build()
        )
    );

    pricesRepository.save(
        PricesMapper.INSTANCE.pricesToPricesRepositoryEntity(
            Prices.builder()
                  .brandId(BRAND_ID)
                  .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                  .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                  .productId(PRODUCT_ID)
                  .priority(true)
                  .price(25.45f)
                  .currency(Currency.EUR)
                  .build()
        )
    );

    pricesRepository.save(
        PricesMapper.INSTANCE.pricesToPricesRepositoryEntity(
            Prices.builder()
                  .brandId(BRAND_ID)
                  .startDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0))
                  .endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0))
                  .productId(PRODUCT_ID)
                  .priority(true)
                  .price(30.5f)
                  .currency(Currency.EUR)
                  .build()
        )
    );

    pricesRepository.save(
        PricesMapper.INSTANCE.pricesToPricesRepositoryEntity(
            Prices.builder()
                  .brandId(BRAND_ID)
                  .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                  .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                  .productId(PRODUCT_ID)
                  .priority(true)
                  .price(38.95f)
                  .currency(Currency.EUR)
                  .build()
        )
    );

  }

  @AfterEach
  void cleanUp() {
    pricesRepository.deleteAll();
  }

  @Test
  public void testFindPrice1_ok() throws Exception {

    ClassPathResource expectedJsonResource = new ClassPathResource("expectedJson/prices1.json");
    String expectedJson = StreamUtils.copyToString(expectedJsonResource.getInputStream(), StandardCharsets.UTF_8);
    String url = "/findPrice?dateQuery=2020-06-14T10:00:00&productId=35455&brandId=1";

    mockMvc.perform(get(url).contentType("application/json")
           ).andExpect(status().isOk())
           .andExpect(content().json(expectedJson));
  }

  @Test
  public void testFindPrice2_ok() throws Exception {
    ClassPathResource expectedJsonResource = new ClassPathResource("expectedJson/prices2.json");
    String expectedJson = StreamUtils.copyToString(expectedJsonResource.getInputStream(), StandardCharsets.UTF_8);
    String url = "/findPrice?dateQuery=2020-06-14T16:00:00&productId=35455&brandId=1";

    mockMvc.perform(get(url)
                        .contentType("application/json")
           ).andExpect(status().isOk())
           .andExpect(content().json(expectedJson));
  }

  @Test
  public void testFindPrice3_ok() throws Exception {
    ClassPathResource expectedJsonResource = new ClassPathResource("expectedJson/prices1.json");
    String expectedJson = StreamUtils.copyToString(expectedJsonResource.getInputStream(), StandardCharsets.UTF_8);
    String url = "/findPrice?dateQuery=2020-06-14T21:00:00&productId=35455&brandId=1";
    mockMvc.perform(get(url).contentType("application/json"))
           .andExpect(status().isOk())
           .andExpect(content().json(expectedJson));
  }

  @Test
  public void testFindPrice4_ok() throws Exception {
    ClassPathResource expectedJsonResource = new ClassPathResource("expectedJson/prices3.json");
    String expectedJson = StreamUtils.copyToString(expectedJsonResource.getInputStream(), StandardCharsets.UTF_8);
    String url = "/findPrice?dateQuery=2020-06-15T10:00:00&productId=35455&brandId=1";

    mockMvc.perform(get(url).contentType("application/json"))
           .andExpect(status().isOk())
           .andExpect(content().json(expectedJson));
  }

  @Test
  public void testFindPrice5_ok() throws Exception {
    ClassPathResource expectedJsonResource = new ClassPathResource("expectedJson/prices4.json");
    String expectedJson = StreamUtils.copyToString(expectedJsonResource.getInputStream(), StandardCharsets.UTF_8);
    String url = "/findPrice?dateQuery=2020-06-15T21:00:00&productId=35455&brandId=1";

    mockMvc.perform(get(url).contentType("application/json"))
           .andExpect(status().isOk())
           .andExpect(content().json(expectedJson));
  }

}

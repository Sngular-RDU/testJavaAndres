# Test Java

This document outlines the decisions made for the implementation of the service, developed in Java with Spring Boot, which exposes a REST
endpoint to retrieve the sale prices of products for a specific distribution chain.

# Author

**Andrés Reinaldo Cid**

## Technologies used

- Java 17
- JUnit
- Spring Boot 2.6.6
- Maven
- H2 Database
- Open API
- IDE IntelliJ

## Microservice structure

The microservice has been developed using a hexagonal architecture as its structural foundation, applying specific design methodologies for
distributed systems during its implementation. We have the following organization:

- **Api**: This package contains the controllers and endpoints that expose the application's functionality to clients. It serves as the
  entry point for HTTP (or other protocol) requests and is responsible for receiving, validating, and routing requests to the corresponding
  services.

- **Infrastructure**: This module handles communication with external systems. It includes the interfaces and classes that define and
  execute persistence operations, whether through JPA, JDBC, or other data access mechanisms.

- **Auth**: This module is dedicated to the configuration and management of application security. In this case, due to the low complexity of
  the application, the use of JWT has been disabled, and a simple Spring Security configuration has been implemented.

- **Model**: This package contains the classes that represent the domain models. These classes define the structure of the data used by the
  application, reflecting the entities of the domain model.

- **Service**: This module contains the classes that implement the business logic. The services act as intermediaries between the Api layer
  and the models, encapsulating the domain rules and operations.

## Notable dependencies

To simplify data mapping between different layers of the application, MapStruct has been used, a library that enables the conversion of
objects of different types through source code annotations. Additionally, Lombok has been employed to reduce boilerplate code, improving
readability and facilitating maintenance.

For API documentation, OpenAPI has been implemented along with Sngular's multiapi plugin, which allows for the automatic generation of API
documentation and, at the same time, the creation of code based on that documentation.

[Documentation API](/schemas_apis/prueba_tecnica_api.yml)

## Database used

Following the task specifications, an **H2 database** has been utilized to store sample data. Two databases have been configured: one for
the development environment and another for the testing environment. This setup leverages profile configurations to manage the databases
used by the application, allowing for the use of distinct datasets during testing as opposed to development. This separation not only
simplifies debugging and code testing but also facilitates the use of different types of databases through profile management.

The creation of schemas and data insertion has been accomplished using SQL scripts, which employ Spring Boot’s default scripts for table
creation and data population. These scripts are located in the application's resources folder, alongside the database connection
configuration files

[Scripts and Configuration](/src/main/resources)

[Example Data](/src/main/resources/data.sql)

[Schemas](/src/main/resources/schema.sql)

## API

A **REST endpoint** has been implemented to retrieve the sale prices of products for a specific distribution chain. This endpoint accepts
the following input parameters:

- **Application date**: The date for which the price is being queried.
- **Product identifier**: Unique code of the product.
- **Chain identifier**: Unique code of the distribution chain.

As a response, the endpoint returns the following data:

- **Product identifier**: Unique code of the product.
- **Chain identifier**: Unique code of the distribution chain.
- **Rate to apply**: Identifier of the corresponding rate.
- **Application dates**: Date range during which the price is valid.
- **Final price to apply**: Calculated price for the product on the specified date.

**Endpoint access:**

`localhost:8080/findPrice`

**Example request:**

`localhost:8080/findPrice?startDate=2020-06-15T16:00:00&productId=35455&brandId=1`

**Example response:**

``` json
{
    "startDate": "2020-06-15T16:00:00",
    "brandId": 1,
    "endDate": "2020-12-31T23:59:59",
    "priceList": 4,
    "productId": 35455,
    "price": 38.95
}
```

## Bussiness logic

To comply with the specification, a service class named PricingService has been implemented. This service is responsible for retrieving the
sale prices of products for a specific distribution chain. It performs an optimized query to the SQL database to directly fetch the
requested data, avoiding the need for complex business logic in the application.

The query is executed using JPQL (Java Persistence Query Language). It is worth noting that, to meet the given constraint (if two prices
coincide on the same date, the one with the highest priority is selected), the results are sorted by priority in descending order, and the
selection is limited to a single result.

[Query JPQL location](/src/main/java/com/example/prueba/tecnica/pruebaTecnica/infrastructure/repositories/PricesRepository.java)

```Java

@Query(
    "SELECT p FROM PricesRepositoryEntity p WHERE p.productId = :productId AND p.brandId = :brandId AND :dateQuery BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC LIMIT 1")
Optional<PricesRepositoryEntity> findPricesBetweenDates(
    @Param("dateQuery") LocalDateTime dateQuery,
    @Param("productId") Long productId,
    @Param("brandId") Long brandId);

```

## Tests

The tests are focused on verifying the requirements outlined in the specification. As previously mentioned, JUnit was used to execute the
tests, interacting with a dedicated test environment database. Test data is initialized before each test and cleaned up after each test
execution to ensure isolation.

MockMvc is employed to simulate requests to the mocked service and validate the responses. For each request, both the HTTP status and the
response content are verified, ensuring that the returned results match the expected ones. The expected JSON responses are stored in the
test resources folder for comparison.

[Test location](/src/test/java/com/example/prueba/tecnica/pruebaTecnica/PruebaTecnicaApplicationTests.java)

## Usage manual

To run the service, follow these steps:

1. Open a terminal in the project folder.

2. Run the command `mvn clean install` to compile the project and install the package in the local Maven repository.

3. Run the command ``mvn spring-boot:run`` to start the development server.

4. Open a second terminal and run the command
   ``curl -X GET "http://localhost:8080/findPrice?startDate=2020-06-15T16:00:00&productId=35455&brandId=1"`` to make a request to the
   service.

To run the tests, follow these steps:

1. Open a terminal in the project folder.

2. Run ``mvn test`` to execute the tests.

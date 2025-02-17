DROP TABLE IF EXISTS prices;

CREATE TABLE prices
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_id   BIGINT       NOT NULL,
    start_date TIMESTAMP    NOT NULL,
    end_date   TIMESTAMP    NOT NULL,
    product_id BIGINT       NOT NULL,
    priority   BOOLEAN      NOT NULL,
    price      FLOAT        NOT NULL,
    currency   VARCHAR(255) NOT NULL
)
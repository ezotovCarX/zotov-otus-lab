CREATE TABLE store_schema.spr_product
(
    id           BIGSERIAL           NOT NULL PRIMARY KEY UNIQUE,
    external_id  VARCHAR(255) unique not null,
    name         VARCHAR(255),
    product_type VARCHAR(255)        not null,
    count        INTEGER             not null
);

CREATE INDEX spr_product_external_id_idx ON store_schema.spr_product (external_id);

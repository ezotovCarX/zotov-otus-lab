CREATE TABLE dictionary_schema.car
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    car_id    UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    power     INT          NOT NULL,
    max_speed INT          NOT NULL
);

COMMENT ON TABLE dictionary_schema.car IS 'Автомобили';
COMMENT ON COLUMN dictionary_schema.car.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN dictionary_schema.car.id IS 'Внешний идентификатор';
COMMENT ON COLUMN dictionary_schema.car.name IS 'Наименование авто';
COMMENT ON COLUMN dictionary_schema.car.power IS 'Мощность';
COMMENT ON COLUMN dictionary_schema.car.max_speed IS 'Максимальная скорость';

CREATE TABLE car_schema.car
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    name      VARCHAR(100) NOT NULL,
    power     INT          NOT NULL,
    max_speed INT          NOT NULL
);

COMMENT ON TABLE car_schema.car IS 'Автомобили';
COMMENT ON COLUMN car_schema.car.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN car_schema.car.name IS 'Наименование авто';
COMMENT ON COLUMN car_schema.car.power IS 'Мощность';
COMMENT ON COLUMN car_schema.car.max_speed IS 'Максимальная скорость';

CREATE TABLE car.car
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    name      VARCHAR(100) NOT NULL,
    power     INT          NOT NULL,
    max_speed INT          NOT NULL
);

COMMENT ON TABLE car.car IS 'Автомобили';
COMMENT ON COLUMN car.car.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN car.car.name IS 'Наименование авто';
COMMENT ON COLUMN car.car.power IS 'Мощность';
COMMENT ON COLUMN car.car.max_speed IS 'Максимальная скорость';

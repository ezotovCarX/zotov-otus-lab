INSERT INTO car.car
VALUES (1, 'Audi TT', 284, 236);
INSERT INTO car.car
VALUES (1, 'Chevrolet Camaro', 311, 344);

SELECT setval('car.car_id_seq', 3);

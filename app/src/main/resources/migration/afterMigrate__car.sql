INSERT INTO car_schema.car
VALUES (1, 'Audi TT', 284, 236);
INSERT INTO car_schema.car
VALUES (2, 'Chevrolet Camaro', 311, 344);

SELECT setval('car_schema.car_id_seq', 3);

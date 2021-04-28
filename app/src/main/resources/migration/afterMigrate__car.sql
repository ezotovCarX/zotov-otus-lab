INSERT INTO car_schema.car
VALUES (1, 'Audi TT', 284, 236)
ON CONFLICT DO NOTHING;
INSERT INTO car_schema.car
VALUES (2, 'Chevrolet Camaro', 311, 344)
ON CONFLICT DO NOTHING;

SELECT setval('car_schema.car_id_seq', 2);

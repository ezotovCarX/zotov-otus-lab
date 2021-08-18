INSERT INTO dictionary_schema.car
VALUES (1, 'c6c496b9-1fb7-400e-a662-f01b7f25df82', 'Audi TT', 284, 236)
ON CONFLICT DO NOTHING;
INSERT INTO dictionary_schema.car
VALUES (2, 'd5422001-a1a1-4487-97c3-55e627ab23a5', 'Chevrolet Camaro', 311, 344)
ON CONFLICT DO NOTHING;

SELECT setval('dictionary_schema.car_id_seq', 2);

INSERT INTO dictionary_schema.reward
VALUES (1, 'MONEY', 'Награда за заезд', 100)
ON CONFLICT DO NOTHING;

SELECT setval('dictionary_schema.reward_id_seq', 1);

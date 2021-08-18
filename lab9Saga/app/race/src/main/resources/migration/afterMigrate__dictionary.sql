INSERT INTO race_schema.race_template
VALUES (1, 1, 'Пустыня', 284, 1, 1)
ON CONFLICT DO NOTHING;
INSERT INTO race_schema.race_template
VALUES (1, 1, 'Шоссе', 325, 2, 2)
ON CONFLICT DO NOTHING;

SELECT setval('race_schema.race_template_id_seq', 2);

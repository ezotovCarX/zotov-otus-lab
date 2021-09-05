INSERT INTO race_schema.race_template
VALUES (1, 1, 'Пустыня', 284, 1, 1, FALSE)
ON CONFLICT DO NOTHING;
INSERT INTO race_schema.race_template
VALUES (2, 1, 'Шоссе', 325, 2, 2, TRUE)
ON CONFLICT DO NOTHING;

SELECT setval('race_schema.race_template_id_seq', 2);

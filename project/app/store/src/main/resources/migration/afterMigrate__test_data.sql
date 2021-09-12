INSERT INTO store_schema.spr_product
VALUES (1, 'fuel_3', 'Полный бак', 'FUEL', 3)
ON CONFLICT DO NOTHING;
SELECT setval('store_schema.spr_product_id_seq', 1);

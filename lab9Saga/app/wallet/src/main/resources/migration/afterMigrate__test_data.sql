INSERT INTO wallet_schema.wallet
VALUES (1, '179a1fb0-4d53-47c5-bb31-703631539b8d', 10, 10000)
ON CONFLICT DO NOTHING;

INSERT INTO wallet_schema.reward
VALUES (1, 'MONEY', 'Награда за заезд', 100)
ON CONFLICT DO NOTHING;

SELECT setval('wallet_schema.reward_id_seq', 1);
SELECT setval('wallet_schema.wallet_id_seq', 1);

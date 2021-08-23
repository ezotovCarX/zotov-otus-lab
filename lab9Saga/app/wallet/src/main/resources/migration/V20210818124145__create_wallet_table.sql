CREATE TABLE wallet_schema.wallet
(
    id         BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    profile_Id UUID,
    fuel       INT       NOT NULL,
    money      INT       NOT NULL
);

COMMENT ON TABLE wallet_schema.wallet IS 'Ресурсы игрока';
COMMENT ON COLUMN wallet_schema.wallet.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN wallet_schema.wallet.profile_Id IS 'Ид профиля';
COMMENT ON COLUMN wallet_schema.wallet.fuel IS 'Топливо';
COMMENT ON COLUMN wallet_schema.wallet.money IS 'Деньги';

CREATE INDEX wallet_profile_id_idx ON wallet_schema.wallet (profile_Id);

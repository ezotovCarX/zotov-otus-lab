CREATE TABLE order_schema.order
(
    id         BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    amount     NUMERIC   NOT NULL,
    state      CHAR(255) NOT NULL,
    user_id    BIGINT    NOT NULL,
    request_id UUID      NOT NULL
);

COMMENT ON TABLE order_schema.order IS 'Заказы';
COMMENT ON COLUMN order_schema.order.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN order_schema.order.amount IS 'Сумма';
COMMENT ON COLUMN order_schema.order.state IS 'Статус';
COMMENT ON COLUMN order_schema.order.user_id IS 'Ид пользователя';
COMMENT ON COLUMN order_schema.order.request_id IS 'Ключ идемпотентности';

CREATE INDEX IF NOT EXISTS order_user_id_idx ON order_schema.order (user_id);
CREATE INDEX IF NOT EXISTS order_request_state_id_idx ON order_schema.order (request_id, state);

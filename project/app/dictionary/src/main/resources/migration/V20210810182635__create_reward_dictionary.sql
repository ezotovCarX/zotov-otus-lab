CREATE TABLE dictionary_schema.reward
(
    id    BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    type  VARCHAR(100) NOT NULL,
    name  VARCHAR(255) NOT NULL,
    total INT          NOT NULL
);

COMMENT ON TABLE dictionary_schema.reward IS 'Награды';
COMMENT ON COLUMN dictionary_schema.reward.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN dictionary_schema.reward.type IS 'Вид награды';
COMMENT ON COLUMN dictionary_schema.reward.name IS 'Наименование';
COMMENT ON COLUMN dictionary_schema.reward.total IS 'Кол-во';

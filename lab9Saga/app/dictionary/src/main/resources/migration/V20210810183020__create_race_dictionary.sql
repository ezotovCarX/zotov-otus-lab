CREATE TABLE dictionary_schema.race
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    reward_id BIGINT
        CONSTRAINT dictionary_schema_race_reward
            REFERENCES dictionary_schema.reward
            ON DELETE RESTRICT,
    name      VARCHAR(255) NOT NULL,
    track_id  INT          NOT NULL
);

COMMENT ON TABLE dictionary_schema.race IS 'Заезд';
COMMENT ON COLUMN dictionary_schema.race.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN dictionary_schema.race.reward_id IS 'ИД награды';
COMMENT ON COLUMN dictionary_schema.race.name IS 'Наименование';
COMMENT ON COLUMN dictionary_schema.race.track_id IS 'Ид трассы';

CREATE INDEX dictionary_schema_race_reward_id ON dictionary_schema.race (reward_id);

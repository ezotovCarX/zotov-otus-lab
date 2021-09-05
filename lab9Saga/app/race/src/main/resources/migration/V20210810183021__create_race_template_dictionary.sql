CREATE TABLE race_schema.race_template
(
    id           BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    reward_id    BIGSERIAL    NOT NULL,
    name         VARCHAR(255) NOT NULL,
    track_length INT          NOT NULL,
    fuel_consume INT          NOT NULL,
    track_id     INT          NOT NULL
);

COMMENT ON TABLE race_schema.race_template IS 'Справочник шаблонов заездов';
COMMENT ON COLUMN race_schema.race_template.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN race_schema.race_template.reward_id IS 'ИД награды';
COMMENT ON COLUMN race_schema.race_template.name IS 'Наименование';
COMMENT ON COLUMN race_schema.race_template.track_length IS 'Длинна трассы';
COMMENT ON COLUMN race_schema.race_template.fuel_consume IS 'Расход топлива';
COMMENT ON COLUMN race_schema.race_template.track_id IS 'Ид трассы';

CREATE INDEX race_reward_id_idx ON race_schema.race_template (reward_id);

CREATE TABLE race_schema.race
(
    id               BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    external_Id      UUID,
    profile_Id       UUID,
    race_template_id BIGINT
        CONSTRAINT dictionary_schema_race_template
            REFERENCES race_schema.race_template
            ON DELETE RESTRICT,
    state            VARCHAR(255) NOT NULL
);

COMMENT ON TABLE race_schema.race IS 'Справочник шаблонов заездов';
COMMENT ON COLUMN race_schema.race.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN race_schema.race.external_Id IS 'Внешний ид';
COMMENT ON COLUMN race_schema.race.profile_Id IS 'Ид профиля игрока';
COMMENT ON COLUMN race_schema.race.race_template_id IS 'ИД шаблона заезда';
COMMENT ON COLUMN race_schema.race.state IS 'Состояние заезда';

CREATE INDEX race_race_template_id_idx ON race_schema.race (race_template_id);
CREATE INDEX race_race_external_id_idx ON race_schema.race (external_Id);
CREATE INDEX race_race_profile_id_idx ON race_schema.race (profile_Id);

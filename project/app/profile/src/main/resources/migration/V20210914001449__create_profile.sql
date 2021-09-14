CREATE TABLE profile_schema.profile
(
    id             BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    external_id    uuid      NOT NULL,
    progress       INTEGER   NOT NULL,
    current_car_id INTEGER   NOT NULL,
    score          BIGINT    NOT NULL,
    level          INTEGER   NOT NULL
);

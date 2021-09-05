CREATE TABLE auth_schema.users
(
    id         BIGSERIAL    NOT NULL PRIMARY KEY UNIQUE,
    profile_Id UUID,
    email      VARCHAR(254) NOT NULL,
    nickname   VARCHAR(254) NOT NULL
);

COMMENT ON TABLE auth_schema.users IS 'Пользователи';
COMMENT ON COLUMN auth_schema.users.id IS 'Уникальный идентификатор';
COMMENT ON COLUMN auth_schema.users.profile_Id IS 'Ид профиля';
COMMENT ON COLUMN auth_schema.users.email IS 'Почта';
COMMENT ON COLUMN auth_schema.users.nickname IS 'Ник игрока';

CREATE INDEX auth_profile_id_idx ON auth_schema.users (profile_Id);

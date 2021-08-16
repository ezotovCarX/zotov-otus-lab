-- Скрипт инициализации schemas при первом запуске
CREATE SCHEMA IF NOT EXISTS dictionary_schema;
CREATE SCHEMA IF NOT EXISTS user_schema;
CREATE SCHEMA IF NOT EXISTS flyway_schema;
COMMIT;

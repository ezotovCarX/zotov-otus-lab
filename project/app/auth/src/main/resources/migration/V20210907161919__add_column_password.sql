ALTER TABLE auth_schema.users
    ADD COLUMN IF NOT EXISTS password VARCHAR(255);

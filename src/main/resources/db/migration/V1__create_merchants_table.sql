CREATE TABLE IF NOT EXISTS merchants
(
    id         INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    secret_key TEXT
)
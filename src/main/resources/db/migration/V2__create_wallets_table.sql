CREATE TABLE IF NOT EXISTS wallets
(
    id          INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    merchant_id INTEGER,
    currency    VARCHAR(50),
    balance     DECIMAL,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
)
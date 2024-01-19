CREATE TABLE IF NOT EXISTS payouts
(
    id               UUID PRIMARY KEY,
    merchant_id      INTEGER,
    payment_method   VARCHAR(255),
    amount           DECIMAL,
    currency         VARCHAR(50),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    card_data        JSONB,
    language         VARCHAR(50),
    notification_url TEXT,
    customer         JSONB,
    status           VARCHAR(15),
    message          VARCHAR(50),
    FOREIGN KEY (merchant_id) REFERENCES merchants (id)
)
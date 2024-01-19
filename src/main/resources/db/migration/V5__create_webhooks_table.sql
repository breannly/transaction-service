CREATE TABLE IF NOT EXISTS webhooks
(
    id             UUID PRIMARY KEY,
    payment_method VARCHAR(255),
    amount         VARCHAR(255),
    currency       VARCHAR(50),
    type           VARCHAR(50),
    transaction_id UUID,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    card_data      JSONB,
    language       VARCHAR(50),
    customer       JSONB,
    count          smallserial,
    status         VARCHAR(50),
    message        TEXT
);
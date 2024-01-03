CREATE TABLE IF NOT EXISTS webhooks (
    id BIGSERIAL PRIMARY KEY,
    payment_method VARCHAR(255),
    amount VARCHAR(255),
    currency VARCHAR(50),
    type VARCHAR(50),
    transaction_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    card_data_json TEXT,
    language VARCHAR(50),
    customer_json TEXT,
    status VARCHAR(50),
    message TEXT
);
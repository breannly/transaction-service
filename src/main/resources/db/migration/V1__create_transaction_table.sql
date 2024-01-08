CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    payment_method VARCHAR(255),
    amount INTEGER,
    currency VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    card_data JSONB,
    language VARCHAR(50),
    notification_url TEXT,
    customer JSONB,
    status VARCHAR(10),
    message VARCHAR(50)
);
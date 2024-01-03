CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    payment_method VARCHAR(255),
    amount INTEGER,
    currency VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    card_data_json TEXT,
    language VARCHAR(50),
    notification_url TEXT,
    customer_json TEXT,
    status VARCHAR(10),
    message VARCHAR(50)
);
-- Flyway V1 init
CREATE TABLE subscriptions (
  id VARCHAR(64) PRIMARY KEY,
  customer_id VARCHAR(64) NOT NULL,
  email VARCHAR(128) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE subscription_features (
  id SERIAL PRIMARY KEY,
  subscription_id VARCHAR(64) NOT NULL REFERENCES subscriptions(id) ON DELETE CASCADE,
  code VARCHAR(64) NOT NULL,
  enabled BOOLEAN NOT NULL,
  limit_value INTEGER NULL
);

CREATE TABLE transactions (
  id VARCHAR(64) PRIMARY KEY,
  subscription_id VARCHAR(64) NOT NULL REFERENCES subscriptions(id),
  status VARCHAR(32) NOT NULL,
  total_amount BIGINT NOT NULL CHECK (total_amount >= 0),
  currency CHAR(3) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE transaction_items (
  id SERIAL PRIMARY KEY,
  transaction_id VARCHAR(64) NOT NULL REFERENCES transactions(id) ON DELETE CASCADE,
  sku VARCHAR(64) NOT NULL,
  description VARCHAR(255) NOT NULL,
  unit_price_amount BIGINT NOT NULL CHECK (unit_price_amount >= 0),
  currency CHAR(3) NOT NULL,
  quantity INT NOT NULL CHECK (quantity >= 1),
  subtotal_amount BIGINT NOT NULL CHECK (subtotal_amount >= 0)
);

CREATE TABLE subscription_payments (
  id VARCHAR(64) PRIMARY KEY,
  transaction_id VARCHAR(64) NOT NULL REFERENCES transactions(id) UNIQUE,
  method VARCHAR(32) NOT NULL,
  amount BIGINT NOT NULL CHECK (amount >= 0),
  currency CHAR(3) NOT NULL,
  status VARCHAR(32) NOT NULL,
  provider_token VARCHAR(128) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE idempotency_keys (
  id BIGSERIAL PRIMARY KEY,
  idempotency_key VARCHAR(128) NOT NULL,
  request_hash CHAR(64) NOT NULL,
  status VARCHAR(16) NOT NULL,
  response_payload TEXT NULL,
  response_status INT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  CONSTRAINT uk_idem_key UNIQUE(idempotency_key)
);

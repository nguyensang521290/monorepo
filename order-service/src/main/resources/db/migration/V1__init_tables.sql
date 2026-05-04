CREATE TABLE order_data (
    id uuid PRIMARY KEY,
    status varchar(10) NOT NULL,
    products JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_created_at ON order_data(created_at);
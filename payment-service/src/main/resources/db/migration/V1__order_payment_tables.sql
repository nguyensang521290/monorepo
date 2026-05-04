CREATE TABLE order_payment (
    id uuid,
    order_id uuid,
    status varchar(10) NOT NULL,
    products JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id, order_id)
);

CREATE INDEX idx_order_payment_created_at ON order_payment(created_at);
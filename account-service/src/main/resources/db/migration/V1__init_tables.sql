-- Create enum type for account status
CREATE TYPE account_status AS ENUM ('ACTIVE', 'FROZEN', 'CLOSED');

-- Create account table
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    account_number VARCHAR(50) NOT NULL UNIQUE,
    currency VARCHAR(10) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    status account_status NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT account_number_unique UNIQUE (account_number)
);

-- Create index on account_number for faster lookups
CREATE INDEX idx_account_account_number ON account(account_number);

-- Create index on customer_id for faster queries by customer
CREATE INDEX idx_account_customer_id ON account(customer_id);

-- (legacy account_balance table removed; balance now stored on account table)

-- Add comment for documentation
COMMENT ON TABLE account IS 'Stores bank account information';
COMMENT ON TABLE account IS 'Stores bank account information and current balance';
COMMENT ON COLUMN account.status IS 'Account status: ACTIVE, FROZEN, or CLOSED';
COMMENT ON COLUMN account.balance IS 'Current account balance in account currency';
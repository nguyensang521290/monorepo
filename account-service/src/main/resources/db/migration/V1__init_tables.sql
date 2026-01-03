-- Create enum type for account status
CREATE TYPE account_status AS ENUM ('ACTIVE', 'FROZEN', 'CLOSED');

-- Create account table
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL,
    account_number VARCHAR(50) NOT NULL UNIQUE,
    currency VARCHAR(10) NOT NULL,
    status account_status NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT account_number_unique UNIQUE (account_number)
);

-- Create index on account_number for faster lookups
CREATE INDEX idx_account_account_number ON account(account_number);

-- Create index on customer_id for faster queries by customer
CREATE INDEX idx_account_customer_id ON account(customer_id);

-- Create account_balance table
CREATE TABLE account_balance (
    account_id BIGINT NOT NULL PRIMARY KEY,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account_balance_account
     FOREIGN KEY (account_id)
         REFERENCES account(id)
         ON DELETE CASCADE
);

-- Create index on balance for potential queries (optional)
CREATE INDEX idx_account_balance_balance ON account_balance(balance);

-- Add comment for documentation
COMMENT ON TABLE account IS 'Stores bank account information';
COMMENT ON TABLE account_balance IS 'Stores current balance for each account';
COMMENT ON COLUMN account.status IS 'Account status: ACTIVE, FROZEN, or CLOSED';
COMMENT ON COLUMN account_balance.balance IS 'Current account balance in account currency';
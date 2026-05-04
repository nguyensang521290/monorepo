CREATE TABLE outbox_events (
    id uuid PRIMARY KEY,
    key BYTEA NOT NULL,
    payload BYTEA NOT NULL,
    is_published BOOLEAN DEFAULT FALSE,
    event_type VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_outbox_events_created_at ON outbox_events(created_at);
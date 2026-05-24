CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    method VARCHAR(10) NOT NULL,
    path_pattern VARCHAR(255) NOT NULL,
    required_role VARCHAR(20) NOT NULL,
    description VARCHAR(255)
);

-- Seed initial permissions
-- Customers (ROLE_USER)
INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('POST', '**/accounts', 'ROLE_USER', 'Allow customers to open accounts');

-- Admins (ROLE_ADMIN)
INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('POST', '**/accounts/*/deposit', 'ROLE_ADMIN', 'Allow admins to deposit money');

INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('PATCH', '**/accounts/*/balance', 'ROLE_ADMIN', 'Allow admins to update balance');

INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('DELETE', '**/accounts/*', 'ROLE_ADMIN', 'Allow admins to close accounts');

-- Default Order Service permission for customers
INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('ANY', '**/order-service/**', 'ROLE_USER', 'Default access to order service');

-- Identity Service permissions (usually public or self-service, but good to have)
INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('ANY', '**/auth/**', 'ANY', 'Public auth endpoints');

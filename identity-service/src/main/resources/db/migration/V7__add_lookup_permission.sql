INSERT INTO permissions (method, path_pattern, required_role, description) 
VALUES ('GET', '**/accounts/customer/*', 'ANY', 'Allow lookup account by customer ID');

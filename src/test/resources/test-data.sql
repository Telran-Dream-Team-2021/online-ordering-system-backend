INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users (hash_password, display_name, delivery_address, email)
VALUES ('$2a$10$0d.gqun7BTHuD1lNHDNWAujVXkHwcpZIXGiXb8oJvA/JbJjfKcrpm', 'Mr Adminsky', null, 'admin@tel-ran.co.il');

INSERT INTO users_roles (user_id, roles_id) VALUES (1, 2);
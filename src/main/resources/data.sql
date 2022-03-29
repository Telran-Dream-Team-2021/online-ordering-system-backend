INSERT INTO categories (name) VALUES ('bulki'), ('pirogi');

INSERT INTO products (id, name, category_id, description, image_url, price, unit_of_measurement, is_active)
VALUES (1, 'prod1', 1, 'tra ta ta la la la', '', 123.23, 'ML', true);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users (hash_password, display_name, delivery_address, email)
VALUES ('$2a$10$0d.gqun7BTHuD1lNHDNWAujVXkHwcpZIXGiXb8oJvA/JbJjfKcrpm', 'Mr Adminsky', null, 'admin@tel-ran.co.il');

INSERT INTO users_roles (user_id, roles_id) VALUES (1, 2);
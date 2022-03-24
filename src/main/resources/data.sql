insert into categories (name) values ('bulki'), ('pirogi');

insert into products (id, name, category_id, description, image_url, price, unit_of_measurement, is_active)
VALUES (1, 'prod1', 1, 'tra ta ta la la la', '', 123.23, 'ML', true);

insert into users (id, hash_assword, role, display_name, delivery_address, email)
VALUES (1, '$2a$10$0d.gqun7BTHuD1lNHDNWAujVXkHwcpZIXGiXb8oJvA/JbJjfKcrpm', 'ADMIN', 'Mr Adminsky', '', 'admin@tel-ran.co.il')
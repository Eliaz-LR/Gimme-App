-- Insertion dans la table customer
INSERT INTO customer (id, username, password, name) VALUES (UUID(), 'user1', 'password1', 'User One');
INSERT INTO customer (id, username, password, name) VALUES (UUID(), 'user2', 'password2', 'User Two');
INSERT INTO customer (id, username, password, name) VALUES (UUID(), 'user3', 'password3', 'User Three');

-- Insertion dans la table offer
INSERT INTO offer (id, name, description, category, is_active) VALUES (UUID(), 'Laptop', 'High performance laptop', 'ELECTRONICS', true);
INSERT INTO offer (id, name, description, category, is_active) VALUES (UUID(), 'Winter Jacket', 'Warm winter jacket for cold weather', 'FASHION', true);
INSERT INTO offer (id, name, description, category, is_active) VALUES (UUID(), 'Sofa', 'Comfortable 3-seat sofa for living room', 'HOME', true);
INSERT INTO offer (id, name, description, category, is_active) VALUES (UUID(), 'Book', 'A book about Spring Boot development', 'OTHER', true);

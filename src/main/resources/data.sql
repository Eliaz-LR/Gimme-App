-- Insérer des clients dans la table customer
INSERT INTO customer (username, password, name)
VALUES
    ('user1', '{noop}password1', 'User One'),
    ('user2', '{noop}password2', 'User Two'),
    ('user3', '{noop}password3', 'User Three');

-- Insérer des offres dans la table offer
INSERT INTO offer (id, name, description, category, condition, posted_date, postcode, keywords, can_be_sent_by_post, is_active)
VALUES
    (UUID(), 'Laptop', 'High performance laptop', 'ELECTRONICS', 'GOOD', CURRENT_TIMESTAMP, '75001', 'computer', true, true),
    (UUID(), 'Winter Jacket', 'Warm winter jacket for cold weather', 'FASHION', 'VERY_GOOD', CURRENT_TIMESTAMP, '69001', 'jacket', false, true),
    (UUID(), 'Sofa', 'Comfortable 3-seat sofa for living room', 'HOME', 'GOOD', CURRENT_TIMESTAMP, '13001', 'sofa', false, true),
    (UUID(), 'Book', 'A book about Spring Boot development', 'OTHER', 'NEW_WITHOUT_TAG', CURRENT_TIMESTAMP, '33001', 'book', true, true),
    (UUID(), 'Smartphone', 'Latest model smartphone with high-end features', 'ELECTRONICS', 'NEW_WITH_TAG', CURRENT_TIMESTAMP, '75001', 'phone', true, true),
    (UUID(), 'Dining Table', 'Wooden dining table for 6 people', 'HOME', 'VERY_GOOD', CURRENT_TIMESTAMP, '69001', 'table', false, true),
    (UUID(), 'Gaming Console', 'Gaming console with two controllers', 'ELECTRONICS', 'GOOD', CURRENT_TIMESTAMP, '13001', 'console', true, true),
    (UUID(), 'Bicycle', 'Mountain bike in excellent condition', 'OTHER', 'VERY_GOOD', CURRENT_TIMESTAMP, '33001', 'bike', false, true),

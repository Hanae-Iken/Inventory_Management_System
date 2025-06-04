-- Insert customer user
INSERT INTO users (username, password, role) VALUES
('customer2', '$2a$10$7EqJtq98hPqEXAMPLEHASHED1bBo4OjF.lGykj8IEa9wJ3h0Nw0z2S', 'CUSTOMER');

-- Insert items
INSERT INTO items (name, description, price, created_at) VALUES
('Laptop Dell XPS 13', 'High-performance laptop with Intel i7 processor, 16GB RAM, 512GB SSD.', 999.99, NOW()),
('iPhone 14 Pro', 'Apple smartphone with A16 Bionic chip and triple-camera system.', 1199.00, NOW()),
('Samsung Galaxy S23 Ultra', 'Flagship Android phone with 200MP camera and stylus support.', 1299.99, NOW()),
('MacBook Air M2', 'Lightweight Apple laptop with M2 chip and 18-hour battery life.', 1149.49, NOW()),
('HP Envy x360', 'Convertible 2-in-1 laptop with touchscreen and AMD Ryzen 7.', 849.00, NOW()),
('Asus ROG Strix G15', 'Gaming laptop with RTX 3060 and 144Hz display.', 1399.00, NOW()),
('Google Pixel 7', 'Google smartphone with pure Android experience and great camera.', 599.99, NOW()),
('Lenovo ThinkPad X1 Carbon', 'Business-class ultrabook with high durability and performance.', 1290.50, NOW()),
('OnePlus 11', 'Flagship killer phone with Snapdragon 8 Gen 2 and 100W fast charging.', 699.00, NOW()),
('iPad Air 5', 'Apple tablet with M1 chip and USB-C support for professionals.', 749.99, NOW());

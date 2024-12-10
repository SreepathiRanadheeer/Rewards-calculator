-- Insert data into customer table
INSERT INTO customer (name) VALUES ('Rana'), ('Sreepathi');

-- Insert data into transaction table
INSERT INTO transaction (transaction_date, amount, customer_id) VALUES
('2024-10-01', 120, 1),
('2024-11-05', 90, 1),
('2024-09-15', 130, 2),
('2024-11-20', 75, 2);

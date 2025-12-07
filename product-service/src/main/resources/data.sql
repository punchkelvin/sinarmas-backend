-- Insurance Type
INSERT IGNORE INTO insurance_type (id, name) VALUES (1, 'HEALTH');
INSERT IGNORE INTO insurance_type (id, name) VALUES (2, 'VEHICLE');
INSERT IGNORE INTO insurance_type (id, name) VALUES (3, 'LIFE');

-- Health Product
INSERT IGNORE INTO insurance_product (id, name, base_premium, coverage_amount, currency, insurance_type_id)
VALUES (1, 'Basic Health Guard', 150000.00, 50000000.00, 'IDR', 1);

-- Vehicle Product
INSERT IGNORE INTO insurance_product (id, name, base_premium, coverage_amount, currency, insurance_type_id)
VALUES (2, 'Total Loss Protection', 300000.00, 250000000.00, 'IDR', 2);

-- Life Product
INSERT IGNORE INTO insurance_product (id, name, base_premium, coverage_amount, currency, insurance_type_id)
VALUES (3, 'Family Future Secure', 100000.00, 1000000000.00, 'IDR', 3);
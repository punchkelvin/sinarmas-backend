CREATE DATABASE IF NOT EXISTS product_db;
CREATE DATABASE IF NOT EXISTS customer_db;
CREATE DATABASE IF NOT EXISTS policy_db;
CREATE DATABASE IF NOT EXISTS payment_db;
CREATE DATABASE IF NOT EXISTS claim_db;
-- create new user with username app_user, @'%' allows the user to connect to the database from any ip address with pw sinarmas123
CREATE USER 'app_user'@'%' IDENTIFIED BY 'sinarmas123';
-- gives full power (crud) drop table, shut down server, delete data
-- first * represetn all databases, secodn * represet all tables -> similar to root user / superadmin
GRANT ALL PRIVILEGES ON *.* TO 'app_user'@'%';
-- ensure the changes take efect immediately without restarting the server
FLUSH PRIVILEGES;
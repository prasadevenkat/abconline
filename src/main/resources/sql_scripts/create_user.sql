-- create user testuser
CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'testuser';

-- grant privileges
GRANT ALL PRIVILEGES ON * . * TO 'testuser'@'localhost';

FLUSH PRIVILEGES;
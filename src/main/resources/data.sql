
DROP TABLE IF EXISTS accounts;


CREATE TABLE accounts (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              balance DECIMAL(19, 2) NOT NULL
);


INSERT INTO accounts (balance) VALUES (1000.00);
INSERT INTO accounts (balance) VALUES (500.50);
INSERT INTO accounts (balance) VALUES (250.75);
INSERT INTO accounts (balance) VALUES (10000.00);
INSERT INTO accounts (balance) VALUES (0.00);

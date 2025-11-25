CREATE DATABASE bank;
USE bank;

CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE,
                       password_hash VARCHAR(255),
                       full_name VARCHAR(100),
                       email VARCHAR(100),
                       role VARCHAR(20)
);

CREATE TABLE accounts (
                          account_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          user_id INT,
                          account_type VARCHAR(20),
                          balance DECIMAL(12,2),
                          FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
                              transaction_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              account_id BIGINT,
                              type VARCHAR(30),
                              amount DECIMAL(12,2),
                              timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              description VARCHAR(255),
                              FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

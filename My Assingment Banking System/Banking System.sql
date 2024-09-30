-- CREATE DATABASE HMBank;
-- USE HMBank;
-- CREATE TABLE Customers (
--     customer_id INT PRIMARY KEY AUTO_INCREMENT,
--     first_name VARCHAR(100) NOT NULL,
--     last_name VARCHAR(100) NOT NULL,
--     DOB DATE NOT NULL,
--     email VARCHAR(150) UNIQUE NOT NULL,
--     phone_number VARCHAR(15) NOT NULL,
--     address VARCHAR(255)
-- );

-- CREATE TABLE Accounts (
--     account_id INT PRIMARY KEY AUTO_INCREMENT,
--     customer_id INT,
--     account_type ENUM('savings', 'current', 'zero_balance') NOT NULL,
--     balance DECIMAL(15, 2) DEFAULT 0.00,
--     FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
-- );

-- CREATE TABLE Transactions (
--     transaction_id INT PRIMARY KEY AUTO_INCREMENT,
--     account_id INT,
--     transaction_type ENUM('deposit', 'withdrawal', 'transfer') NOT NULL,
--     amount DECIMAL(15, 2) NOT NULL,
--     transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
-- );

-- INSERT INTO Customers (first_name, last_name, DOB, email, phone_number, address)
-- VALUES
-- ('Amit', 'Sharma', '1985-05-15', 'amit.sharma@example.com', '9876543210', '123 MG Road, Delhi'),
-- ('Priya', 'Verma', '1990-08-22', 'priya.verma@example.com', '9876543211', '456 Park Street, Mumbai'),
-- ('Rahul', 'Singh', '1992-11-05', 'rahul.singh@example.com', '9876543212', '789 Ashok Nagar, Kolkata'),
-- ('Suman', 'Gupta', '1988-03-10', 'suman.gupta@example.com', '9876543213', '321 Nehru Place, Bangalore'),
-- ('Ravi', 'Patel', '1995-12-12', 'ravi.patel@example.com', '9876543214', '654 Gandhi Road, Ahmedabad');
-- INSERT INTO Accounts (customer_id, account_type, balance)
-- VALUES
-- (1, 'savings', 50000.00),
-- (2, 'current', 150000.00),
-- (3, 'zero_balance', 0.00),
-- (4, 'savings', 75000.00),
-- (5, 'current', 100000.00);
-- INSERT INTO Transactions (account_id, transaction_type, amount)
-- VALUES
-- (1, 'deposit', 10000.00),
-- (2, 'withdrawal', 5000.00),
-- (3, 'deposit', 20000.00),
-- (4, 'withdrawal', 7500.00),
-- (5, 'transfer', 15000.00);


use hmBank;
show tables;

Select * from accounts;
Select * from customers;
select * from transactions;

INSERT INTO Customers (first_name, last_name, DOB, email, phone_number, address) VALUES
('Amit', 'Sharma', '1990-05-12', 'amit.sharma@gmail.com', '9876543210', 'Mumbai'),
('Rahul', 'Verma', '1985-02-23', 'rahul.verma@gmail.com', '9123456789', 'Delhi'),
('Pooja', 'Patel', '1992-11-05', 'pooja.patel@gmail.com', '9876543211', 'Ahmedabad'),
('Suresh', 'Kumar', '1980-07-20', 'suresh.kumar@gmail.com', '9876543212', 'Bangalore'),
('Ravi', 'Naidu', '1995-03-14', 'ravi.naidu@gmail.com', '9876543213', 'Chennai'),
('Meena', 'Joshi', '1988-08-25', 'meena.joshi@gmail.com', '9876543214', 'Pune'),
('Anjali', 'Reddy', '1993-12-30', 'anjali.reddy@gmail.com', '9876543215', 'Hyderabad'),
('Kiran', 'Gupta', '1996-01-22', 'kiran.gupta@gmail.com', '9876543216', 'Kolkata'),
('Vikas', 'Singh', '1991-09-17', 'vikas.singh@gmail.com', '9876543217', 'Chandigarh'),
('Sunita', 'Yadav', '1982-04-11', 'sunita.yadav@gmail.com', '9876543218', 'Jaipur');

select * from customers;

INSERT INTO Accounts (customer_id, account_type, balance) VALUES
(1, 'savings', 5000.00),
(2, 'current', 20000.00),
(3, 'savings', 15000.00),
(4, 'zero_balance', 0.00),
(5, 'savings', 10000.00),
(6, 'current', 30000.00),
(7, 'savings', 7000.00),
(8, 'current', 25000.00),
(9, 'zero_balance', 0.00),
(10, 'savings', 12000.00);

select * from accounts;

INSERT INTO Transactions (account_id, transaction_type, amount, transaction_date) VALUES
(1, 'deposit', 2000.00, '2024-01-10'),
(2, 'withdrawal', 5000.00, '2024-01-12'),
(3, 'deposit', 4000.00, '2024-02-15'),
(4, 'deposit', 1000.00, '2024-03-05'),
(5, 'withdrawal', 3000.00, '2024-04-18'),
(6, 'transfer', 5000.00, '2024-05-22'),
(7, 'deposit', 6000.00, '2024-06-30'),
(8, 'withdrawal', 7000.00, '2024-07-19'),
(9, 'transfer', 2000.00, '2024-08-01'),
(10, 'deposit', 1500.00, '2024-09-10');

select * from transactions;

SELECT first_name, last_name, account_type, email
FROM Customers
JOIN Accounts ON Customers.customer_id = Accounts.customer_id;


SELECT first_name, last_name, transaction_type, amount, transaction_date
FROM Customers
JOIN Accounts ON Customers.customer_id = Accounts.customer_id
JOIN Transactions ON Accounts.account_id = Transactions.account_id;


UPDATE Accounts
SET balance = balance + 1000 
WHERE account_id = 1; 

Select * from accounts;


SELECT CONCAT(first_name, ' ', last_name) AS full_name
FROM Customers;


UPDATE Accounts
SET balance = 0
WHERE account_type = 'savings'
AND customer_id = 1;

select * from accounts;

DELETE FROM Accounts
WHERE balance = 0 AND account_type = 'savings';

SET SQL_SAFE_UPDATES = 0;
DELETE FROM Accounts 
WHERE balance = 0 AND account_type = 'savings';

SELECT first_name, last_name
FROM Customers
WHERE address LIKE '%Mumbai%';

SELECT balance
FROM Accounts
WHERE account_id = 5;


SELECT account_id, balance
FROM Accounts
WHERE account_type = 'current' AND balance > 1000;


SELECT transaction_type, amount, transaction_date
FROM Transactions
WHERE account_id = 5;

SELECT account_id, balance, (balance * 0.04) AS interest_accrued
FROM Accounts
WHERE account_type = 'savings';

SELECT account_id, balance
FROM Accounts
WHERE balance < 5000;


SELECT first_name, last_name
FROM Customers
WHERE address NOT LIKE '%Delhi%';

SELECT AVG(balance) AS average_balance
FROM Accounts;


SELECT account_id, balance
FROM Accounts
ORDER BY balance DESC
LIMIT 10;

SELECT SUM(amount) AS total_deposits
FROM Transactions
WHERE transaction_type = 'deposit' AND transaction_date = '2024-09-10';  


SELECT 
    MIN(DOB) AS oldest_customer, 
    MAX(DOB) AS newest_customer
FROM Customers;


SELECT 
    t.transaction_id, 
    t.transaction_type, 
    t.amount, 
    t.transaction_date, 
    a.account_type
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id;

SELECT 
    c.customer_id, 
    c.first_name, 
    c.last_name, 
    a.account_id, 
    a.account_type, 
    a.balance
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id;


SELECT 
    t.transaction_id, 
    t.transaction_type, 
    t.amount, 
    t.transaction_date, 
    c.first_name, 
    c.last_name
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
JOIN Customers c ON a.customer_id = c.customer_id
WHERE a.account_id = '4';  


SELECT 
    c.customer_id, 
    c.first_name, 
    c.last_name, 
    COUNT(a.account_id) AS account_count
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id
HAVING COUNT(a.account_id) > 1;


SELECT 
    account_id, 
    SUM(CASE WHEN transaction_type = 'deposit' THEN amount ELSE 0 END) AS total_deposits,
    SUM(CASE WHEN transaction_type = 'withdrawal' THEN amount ELSE 0 END) AS total_withdrawals,
    SUM(CASE WHEN transaction_type = 'deposit' THEN amount ELSE 0 END) -
    SUM(CASE WHEN transaction_type = 'withdrawal' THEN amount ELSE 0 END) AS difference
FROM Transactions
GROUP BY account_id;

SELECT 
    a.account_id,
    (SUM(CASE WHEN t.transaction_type = 'deposit' THEN t.amount ELSE 0 END) -
     SUM(CASE WHEN t.transaction_type = 'withdrawal' THEN t.amount ELSE 0 END)) / 
    COUNT(DISTINCT t.transaction_date) AS average_daily_balance
FROM Accounts a
LEFT JOIN Transactions t ON a.account_id = t.account_id
WHERE t.transaction_date BETWEEN '2024-02-15' AND '2024-09-10'
GROUP BY a.account_id;


SELECT 
    account_type, 
    SUM(balance) AS total_balance
FROM Accounts
GROUP BY account_type;


SELECT 
    a.account_id, 
    COUNT(t.transaction_id) AS transaction_count
FROM Accounts a
JOIN Transactions t ON a.account_id = t.account_id
GROUP BY a.account_id
ORDER BY transaction_count DESC;

SELECT 
    c.customer_id, 
    c.first_name, 
    c.last_name, 
    SUM(a.balance) AS total_balance,
    a.account_type
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, a.account_type
HAVING total_balance > '7000';

SELECT 
    amount, 
    transaction_date, 
    account_id, 
    COUNT(*) AS duplicate_count
FROM Transactions
GROUP BY amount, transaction_date, account_id
HAVING COUNT(*) > 1;

SELECT c.customer_id, c.first_name, c.last_name, MAX(a.balance) AS highest_balance
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
HAVING MAX(a.balance) = (SELECT MAX(balance) FROM Accounts);

SELECT AVG(a.balance) AS average_balance
FROM Accounts a
WHERE a.customer_id IN (
    SELECT customer_id
    FROM Accounts
    GROUP BY customer_id
    HAVING COUNT(account_id) > 1
);

SELECT a.account_id, a.account_type, a.balance
FROM Accounts a
WHERE a.account_id IN (
    SELECT t.account_id
    FROM Transactions t
    WHERE t.amount > (SELECT AVG(amount) FROM Transactions)
);

SELECT c.customer_id, c.first_name, c.last_name
FROM Customers c
WHERE c.customer_id NOT IN (
    SELECT DISTINCT a.customer_id
    FROM Accounts a
    JOIN Transactions t ON a.account_id = t.account_id
);

SELECT SUM(a.balance) AS total_balance
FROM Accounts a
WHERE a.account_id NOT IN (
    SELECT DISTINCT t.account_id
    FROM Transactions t
);


SELECT t.transaction_id, t.amount, t.transaction_date, a.account_id
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
WHERE a.balance = (SELECT MIN(balance) FROM Accounts);

SELECT t.transaction_id, t.amount, t.transaction_date, a.account_id
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
WHERE a.balance = (SELECT MIN(balance) FROM Accounts);


SELECT c.customer_id, c.first_name, c.last_name
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id
HAVING COUNT(DISTINCT a.account_type) > 1;


SELECT a.account_type,
       COUNT(*) AS count,
       (COUNT(*) / (SELECT COUNT(*) FROM Accounts) * 100) AS percentage
FROM Accounts a
GROUP BY a.account_type;


SELECT t.transaction_id, t.amount, t.transaction_date
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
WHERE a.customer_id = 2; 

SELECT a.account_type,
       SUM(a.balance) AS total_balance,
       (SELECT COUNT(*) FROM Accounts) AS total_accounts
FROM Accounts a
GROUP BY a.account_type;











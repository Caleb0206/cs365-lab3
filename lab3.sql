create table Customer (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	ssn VARCHAR(20) UNIQUE NOT NULL,
	name VARCHAR(50) NOT NULL,
	address VARCHAR(100) ,
	phone_number VARCHAR(15)
);

create table CreditCard (
	number INTEGER PRIMARY KEY,
	type ENUM('Visa', 'MC', 'American_Express','Discover') NOT NULL,
	credit_limit DECIMAL(10,2) NOT NULL,
	balance DECIMAL(10,2) DEFAULT 0,
	active BOOLEAN DEFAULT FALSE
);

create table Ownership (
	customer_id INTEGER,
    card_number INTEGER,
    is_current BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (customer_id, card_number),
    FOREIGN KEY (customer_id) references Customer(id),
    FOREIGN KEY (card_number) references CreditCard(number)
);
drop table Ownership;

create table Vendor (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

create table Transaction (
	id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    customer_id INT NOT NULL,
    card_number INT NOT NULL,
    vendor_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (customer_id, card_number) references Ownership(customer_id, card_number),
    FOREIGN KEY (vendor_id) references Vendor(id)
);
drop table Transaction;

create table Payment (
	id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    card_number INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (card_number) references CreditCard(number)
);
SELECT COUNT(*) FROM Customer;
SELECT COUNT(*) FROM CreditCard;
SELECT COUNT(*) FROM Vendor;
SELECT COUNT(*) FROM Ownership;
SELECT COUNT(*) FROM Transaction;
SELECT COUNT(*) FROM Payment;
select * from Customer;
select * from CreditCard;
select * from Ownership;
select * from Vendor;
select * from Transaction;
select * from Payment;


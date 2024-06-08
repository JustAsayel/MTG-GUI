create database MTG ;
use MTG;
CREATE TABLE Trip (
  Trip_id INT AUTO_INCREMENT PRIMARY KEY,
    Customer_id INT,
    City_id INT,
    start_date DATE,
    end_date DATE,
    Num_passengers INT,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id),
    FOREIGN KEY (City_id) REFERENCES Cities(City_id)
);

CREATE TABLE Customer (
  Customer_id INT PRIMARY KEY,
  First_name VARCHAR(255) NOT NULL,
  Last_name VARCHAR(255) NOT NULL,
  nationality VARCHAR(255) NOT NULL,
  age INT,
  budget DECIMAL(10,2),
  gender CHAR(1) CHECK (gender IN ('M', 'F')),
  PaymentMethod_id INT,
  Phone_number VARCHAR(255)
);

CREATE TABLE Payment (
  Invoice_number INT PRIMARY KEY,
  amount DECIMAL(10,2) NOT NULL,
  PaymentMethod_id INT,
  PaymentMethod_name VARCHAR(255),
  Customer_id INT,
  Car_number INT,
  Tour_guide_id INT
);

CREATE TABLE additional_services (
  Tour_guide_id INT PRIMARY KEY,
  Tour_guide_name VARCHAR(255) NOT NULL,
  Tour_guide_cost DECIMAL(10,2) NOT NULL,
  Tour_guide_phone VARCHAR(255) NOT NULL,
  Customer_id INT
);

CREATE TABLE Delivery_service (
    Car_number INT AUTO_INCREMENT PRIMARY KEY,
    Car_name VARCHAR(100),
    color VARCHAR(50),
    Num_passengers INT,
    Cost_of_car DECIMAL(10, 2)
   
);


CREATE TABLE Users (
  User_id INT PRIMARY KEY,
  Username VARCHAR(255) NOT NULL,
  Password VARCHAR(255) NOT NULL,
  Email VARCHAR(255),
  Role VARCHAR(50) NOT NULL
);

CREATE TABLE Orders (
 Order_id INT AUTO_INCREMENT PRIMARY KEY,
    Customer_id INT,
    Trip_id INT,
    Order_date DATE,
    Currency_id INT,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id),
    FOREIGN KEY (Trip_id) REFERENCES Trip(Trip_id),
    FOREIGN KEY (Currency_id) REFERENCES Currency(Currency_id)
);

CREATE TABLE Currency (
  Currency_id INT PRIMARY KEY,
  Currency_name VARCHAR(50) NOT NULL,
  Symbol VARCHAR(5) NOT NULL
);

CREATE TABLE IT_Tickets (
 Ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    Technician_name VARCHAR(100),
    Technician_specialization VARCHAR(100),
    Issue_description TEXT,
    Priority VARCHAR(50),
    Status VARCHAR(50),
    Customer_id INT,
    FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id)
);

CREATE TABLE Country (
  Country_id INT PRIMARY KEY,
  Country VARCHAR(50),

);

CREATE TABLE Cities (
  City_id INT PRIMARY KEY,
  City_name VARCHAR(255) NOT NULL,
  Country_id INT
);

CREATE TABLE Budget (
  City_id INT PRIMARY KEY,
  budget DECIMAL(10, 2),
);

CREATE TABLE Customer_Numbers (
  Customer_name VARCHAR(255) NOT NULL,
  Customer_Country VARCHAR(255) NOT NULL,
    Phone_number VARCHAR(15),
  Customer_id INT PRIMARY KEY
);

CREATE TABLE price_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    trip_name VARCHAR(100),
    additional_service_id INT,
    price DECIMAL(10, 2)
);


alter table Trip
add FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id);

alter table Customer
add FOREIGN KEY (PaymentMethod_id) REFERENCES Payment_Methods(PaymentMethod_id);

alter table Payment 
add FOREIGN KEY (PaymentMethod_id) REFERENCES Payment_Methods(PaymentMethod_id),
add FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id),
add FOREIGN KEY (Car_number) REFERENCES Delivery_service(Car_number),
add FOREIGN KEY (Tour_guide_id) REFERENCES additional_services(Tour_guide_id) ;
alter table additional_services
add FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id) ;

alter table Orders
add FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id),
add FOREIGN KEY (Trip_id) REFERENCES Trip(Trip_id),
add FOREIGN KEY (Currency_id) REFERENCES Currency(Currency_id) ;

alter table IT_Tickets
add FOREIGN KEY (Customer_id) REFERENCES Customer(Customer_id);

alter table Cities
add FOREIGN KEY (Country_id) REFERENCES Country(Country_id);

alter table Budget 
add FOREIGN KEY (City_id) REFERENCES Cities(City_id) ;

INSERT INTO Country (Country_id, Country) VALUES (1, 'Saudi Arabia');

INSERT INTO Country (Country_id, Country) VALUES
(1, 'Saudi Arabia');

INSERT INTO Cities (City_id, City_name, Country_id) VALUES
(1, 'Riyadh', 1),
(2, 'Jeddah', 1),
(3, 'Abha', 1),
(4, 'Alula', 1),
(5, 'Eastern Province', 1);

INSERT INTO Customer_Numbers (Customer_name, Customer_Country, Phone_number, Customer_id) VALUES
('John Doe', 'Saudi Arabia', '+966123456789', 1),
('Jane Smith', 'Saudi Arabia', '+966987654321', 2),
('Ahmed Ali', 'Saudi Arabia', '+966555444333', 3),
('Fatima Khan', 'Saudi Arabia', '+966777888999', 4),
('Mohammed Abdullah', 'Saudi Arabia', '+966111222333', 5);

INSERT INTO Customer (Customer_id, First_name, Last_name, nationality, age, budget, gender, PaymentMethod_id, Phone_number) VALUES
(1, 'John', 'Doe', 'Saudi Arabian', 30, 5000.00, 'M', 1, '+966123456789'),
(2, 'Jane', 'Smith', 'American', 25, 7000.00, 'F', 2, '+966987654321'),
(3, 'Ahmed', 'Ali', 'Egyptian', 40, 3000.00, 'M', 1, '+966555444333'),
(4, 'Fatima', 'Khan', 'Pakistani', 35, 6000.00, 'F', 2, '+966777888999'),
(5, 'Mohammed', 'Abdullah', 'Saudi Arabian', 28, 4500.00, 'M', 1, '+966111222333');

INSERT INTO Budget (City_id, budget) VALUES
(1, 1000.00),
(2, 1200.00),
(3, 800.00),
(4, 1500.00),
(5, 2000.00);

INSERT INTO additional_services (Tour_guide_id, Tour_guide_name, Tour_guide_cost, Tour_guide_phone, Customer_id) VALUES
(1, 'Ali Hassan', 100.00, '+966555444333', 1),
(2, 'Fatima Ahmed', 120.00, '+966777888999', 2),
(3, 'Ahmed Mohammed', 80.00, '+966111222333', 3),
(4, 'Layla Khalid', 150.00, '+966123456789', 4),
(5, 'Youssef Salah', 200.00, '+966987654321', 5);

INSERT INTO Trip (Trip_id, Customer_id, City_id, start_date, end_date, Num_passengers) VALUES
(1, 1, 1, '2024-06-01', '2024-06-10', 2),
(2, 2, 2, '2024-07-05', '2024-07-15', 1),
(3, 3, 3, '2024-08-10', '2024-08-20', 3),
(4, 4, 4, '2024-09-15', '2024-09-25', 2),
(5, 5, 5, '2024-10-20', '2024-10-30', 4);

INSERT INTO Payment (Invoice_number, amount, PaymentMethod_id, PaymentMethod_name, Customer_id, Car_number, Tour_guide_id) VALUES
(1, 500.00, 1, 'Credit Card', 1, 1, 1),
(2, 600.00, 2, 'PayPal', 2, 2, 2),
(3, 400.00, 1, 'Credit Card', 3, 3, 3),
(4, 750.00, 2, 'PayPal', 4, 4, 4),
(5, 1000.00, 1, 'Credit Card', 5, 5, 5);

INSERT INTO Delivery_service (Car_number, Car_name, color, Num_passengers, Cost_of_car) VALUES
(1, 'Toyota Camry', 'White', 4, 50.00),
(2, 'Honda Accord', 'Black', 4, 60.00),
(3, 'Kia Sportage', 'Red', 5, 70.00),
(4, 'Hyundai Elantra', 'Blue', 4, 55.00),
(5, 'Nissan Altima', 'Silver', 4, 65.00);

INSERT INTO Users (User_id, Username, Password, Email, Role) VALUES
(1, 'admin', 'admin123', 'admin@example.com', 'Admin'),
(2, 'user1', 'user123', 'user1@example.com', 'User'),
(3, 'user2', 'user456', 'user2@example.com', 'User'),
(4, 'user3', 'user789', 'user3@example.com', 'User'),
(5, 'user4', 'user000', 'user4@example.com', 'User');

INSERT INTO Orders (Order_id, Customer_id, Trip_id, Order_date, Currency_id) VALUES
(1, 1, 1, '2024-05-08', 1),
(2, 2, 2, '2024-05-08', 1),
(3, 3, 3, '2024-05-08', 1),
(4, 4, 4, '2024-05-08', 1),
(5, 5, 5, '2024-05-08', 1);

INSERT INTO IT_Tickets (Ticket_id, Technician_name, Technician_specialization, Issue_description, Priority, Status, Customer_id) VALUES
(1, 'John Smith', 'Database Administrator', 'Unable to login to database', 'High', 'Open', 1),
(2, 'Jane Doe', 'Network Engineer', 'Internet connection is slow', 'Medium', 'In Progress', 2),
(3, 'Ahmed Ali', 'Software Developer', 'Application crashes frequently', 'High', 'Open', 3),
(4, 'Fatima Khan', 'Systems Analyst', 'Printer not working', 'Low', 'Closed', 4),
(5, 'Mohammed Abdullah', 'IT Support Technician', 'Cannot access email', 'Medium', 'In Progress', 5);

INSERT INTO Currency (Currency_id, Currency_name, Symbol) VALUES
(1, 'Saudi Riyal', 'SAR');

INSERT INTO price_table (id, trip_name, additional_service_id, price) VALUES
(1, 'Riyadh Trip', 1, 100.00),
(2, 'Jeddah Trip', 2, 120.00),
(3, 'Abha Trip', 3, 80.00),
(4, 'Alula Trip', 4, 150.00),
(5, 'Eastern Province Trip', 5, 200.00);

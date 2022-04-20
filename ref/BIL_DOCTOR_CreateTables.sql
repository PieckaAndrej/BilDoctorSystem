USE [CSC-CSD-S211_10407543]


ALTER TABLE dbo.Person
DROP CONSTRAINT if exists PersonCityFK;
ALTER TABLE dbo.Employee
DROP CONSTRAINT if exists EmployeePersonFK
ALTER TABLE dbo.Customer
DROP CONSTRAINT if exists CustomerPersonFK;
ALTER TABLE dbo.Phone
DROP CONSTRAINT if exists PhonePersonFK, PhoneCityFK;
ALTER TABLE dbo.Vehicle
DROP CONSTRAINT if exists VehicleCustomerFK;
ALTER TABLE dbo.Sale
DROP CONSTRAINT if exists SaleVehicleFK;
ALTER TABLE dbo.[Service]
DROP CONSTRAINT if exists ServiceSaleDateFK, ServiceSalePlateFK;
ALTER TABLE dbo.OrderLine
DROP CONSTRAINT if exists OrderLineSaleDateFK, OrderLineSalePlateFK, OrderLineProductFK;
ALTER TABLE dbo.Appointment
DROP CONSTRAINT if exists AppointmentSalePlateFK, AppointmentSaleDateFK, AppointmentEmployeeFK, AppointmentCustomerFK;

GO

drop table if exists dbo.City;
drop table if exists dbo.Person;
drop table if exists dbo.Employee;
drop table if exists dbo.Customer;
drop table if exists dbo.Phone;
drop table if exists dbo.Vehicle;
drop table if exists dbo.Sale;
drop table if exists dbo.[Service];
drop table if exists dbo.Product;
drop table if exists dbo.OrderLine;
drop table if exists dbo.Appointment;



CREATE TABLE dbo.City (
	countryCode VARCHAR(5) NOT NULL,
	zipcode VARCHAR(5) NOT NULL,
	city VARCHAR(25) NOT NULL,
	PRIMARY KEY (countryCode, zipcode),
	)  

GO


CREATE TABLE dbo.Person (
	id int PRIMARY KEY IDENTITY(1,1),
	[name] VARCHAR(25) NOT NULL,
	surname VARCHAR(25) NOT NULL,
	zipcode VARCHAR(5),
	[address] VARCHAR(50) NOT NULL,
	association VARCHAR(1) NOT NULL,
	CONSTRAINT PersonCityFK
		FOREIGN KEY (zipcode) REFERENCES City(zipcode)
		ON DELETE SET NULL,
	)  

GO


CREATE TABLE dbo.Employee (
   id int PRIMARY KEY IDENTITY(1,1),  
   salary MONEY NOT NULL,
   CONSTRAINT EmployeePersonFK
		FOREIGN KEY (id) REFERENCES Person(id)
		ON DELETE SET NULL,
	)  

GO

CREATE TABLE dbo.Customer (
   id int PRIMARY KEY IDENTITY(1,1),  
   discount decimal(5,5) NOT NULL,
   CONSTRAINT CustomerPersonFK
		FOREIGN KEY (id) REFERENCES Person(id)
		ON DELETE SET NULL,
	)  

GO


CREATE TABLE dbo.Phone (
	phoneNumber VARCHAR(20) NOT NULL,
	countryCode VARCHAR(5) NOT NULL,
	personId int NOT NULL,
	PRIMARY KEY (phoneNumber, countryCode),
	CONSTRAINT PhonePersonFK
		FOREIGN KEY (personId) REFERENCES Person(id)
		ON DELETE SET NULL,
	CONSTRAINT PhoneCityFK
		FOREIGN KEY (countryCode) REFERENCES City(countryCode)
		ON DELETE SET NULL,
	)  

GO

CREATE TABLE dbo.Vehicle (
	plateNumber VARCHAR(15) PRIMARY KEY NOT NULL,
	[year] int NOT NULL,
	brand VARCHAR(20) NOT NULL,
	customerId int NOT NULL,
	CONSTRAINT VehicleCustomerFK
		FOREIGN KEY (customerId) REFERENCES Customer(id)
		ON DELETE SET NULL,
	)  

GO


CREATE TABLE dbo.Sale (
	plateNumber VARCHAR(15) NOT NULL,
	[date] datetime NOT NULL, 
	[description] VARCHAR(500) NOT NULL,
	PRIMARY KEY (plateNumber, [date]),
	CONSTRAINT SaleVehicleFK
		FOREIGN KEY (plateNumber) REFERENCES Vehicle(plateNumber)
		ON DELETE SET NULL,
	)  

GO

CREATE TABLE dbo.[Service] (
	id int PRIMARY KEY IDENTITY(1,1),  
	[description] VARCHAR(500) NOT NULL,
	[time] decimal(5,2) NOT NULL,
	price MONEY NOT NULL,
	plateNumber VARCHAR(15) NOT NULL,
	[date] datetime NOT NULL,
	CONSTRAINT ServiceSaleDateFK
		FOREIGN KEY ([date]) REFERENCES Sale([date])
		ON DELETE SET NULL,
	CONSTRAINT ServiceSalePlateFK
		FOREIGN KEY (plateNumber) REFERENCES Sale(plateNumber)
		ON DELETE SET NULL,
	)  

GO

CREATE TABLE dbo.Product (
	id int PRIMARY KEY IDENTITY(1,1),  
	currentStock int NOT NULL,
	price MONEY NOT NULL,
	)

GO

CREATE TABLE dbo.OrderLine (
	id int PRIMARY KEY IDENTITY(1,1), 
	quantity int NOT NULL,
	plateNumber VARCHAR(15) NOT NULL,
	[date] datetime NOT NULL,
	productId int,
	CONSTRAINT OrderLineSaleDateFK
		FOREIGN KEY ([date]) REFERENCES Sale([date])
		ON DELETE CASCADE,
	CONSTRAINT OrderLineSalePlateFK
		FOREIGN KEY (plateNumber) REFERENCES Sale(plateNumber)
		ON DELETE CASCADE,
	CONSTRAINT OrderLineProductFK
		FOREIGN KEY (productId) REFERENCES Product(id)
		ON DELETE CASCADE,
	)

GO



CREATE TABLE dbo.Appointment (
	id int PRIMARY KEY IDENTITY(1,1), 
	creationDate datetime NOT NULL,
	[date] datetime NOT NULL,
	[length] decimal(5,2) NOT NULL,
	plateNumber VARCHAR(15),
	saleDate datetime,
	employeeId int,
	customerId int,
	CONSTRAINT AppointmentSalePlateFK
		FOREIGN KEY (plateNumber) REFERENCES Sale(plateNumber)
		ON DELETE SET NULL,
	CONSTRAINT AppointmentSaleDateFK
		FOREIGN KEY (saleDate) REFERENCES Sale([date])
		ON DELETE SET NULL,
	CONSTRAINT AppointmentEmployeeFK
		FOREIGN KEY (employeeId) REFERENCES Employee(id)
		ON DELETE SET NULL,
	CONSTRAINT AppointmentCustomerFK
		FOREIGN KEY (customerId) REFERENCES Customer(id)
		ON DELETE SET NULL,
	)

GO
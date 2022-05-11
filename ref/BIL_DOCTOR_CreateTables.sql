USE [CSC-CSD-S211_10407543]


ALTER TABLE dbo.Person
DROP CONSTRAINT if exists PersonCityFK;
ALTER TABLE dbo.Employee
DROP CONSTRAINT if exists EmployeePersonFK;
ALTER TABLE dbo.Customer
DROP CONSTRAINT if exists CustomerPersonFK;
ALTER TABLE dbo.Vehicle
DROP CONSTRAINT if exists VehicleCustomerFK;
ALTER TABLE dbo.Sale
DROP CONSTRAINT if exists SaleVehicleFK;
ALTER TABLE dbo.[Service]
DROP CONSTRAINT if exists ServiceSaleFK;
ALTER TABLE dbo.OrderLine
DROP CONSTRAINT if exists OrderLineSaleFK, OrderLineProductFK;
ALTER TABLE dbo.Appointment
DROP CONSTRAINT if exists AppointmentEmployeeFK;

GO

drop table if exists dbo.City;
drop table if exists dbo.Person;
drop table if exists dbo.Employee;
drop table if exists dbo.Customer;
drop table if exists dbo.Vehicle;
drop table if exists dbo.Sale;
drop table if exists dbo.[Service];
drop table if exists dbo.Product;
drop table if exists dbo.OrderLine;
drop table if exists dbo.Appointment;



CREATE TABLE dbo.City (
	zipcode VARCHAR(5) PRIMARY KEY NOT NULL,
	city VARCHAR(25) NOT NULL,
	)  
GO


CREATE TABLE dbo.Person ( 
	[name] VARCHAR(25) NOT NULL,
	surname VARCHAR(25) NOT NULL,
	zipcode VARCHAR(5),
	[address] VARCHAR(50) NOT NULL,
	association VARCHAR(1) NOT NULL,
	phoneNumber VARCHAR(20) PRIMARY KEY,
	CONSTRAINT PersonCityFK
		FOREIGN KEY (zipcode) REFERENCES City(zipcode)
		ON DELETE SET NULL,
	)  

GO


CREATE TABLE dbo.Employee (
   salary MONEY NOT NULL,
   phoneNo VARCHAR(20) PRIMARY KEY,
   CONSTRAINT EmployeePersonFK
		FOREIGN KEY (phoneNo) REFERENCES Person(phoneNumber)
		ON DELETE CASCADE,
	)  

GO

CREATE TABLE dbo.Customer (
   discount decimal(5,5) NOT NULL,
   phoneNo VARCHAR(20) PRIMARY KEY,
   CONSTRAINT CustomerPersonFK
		FOREIGN KEY (phoneNo) REFERENCES Person(phoneNumber)
		ON DELETE CASCADE,
	)  

GO

CREATE TABLE dbo.Vehicle (
	plateNumber VARCHAR(15) PRIMARY KEY NOT NULL,
	[year] int NOT NULL,
	brand VARCHAR(20) NOT NULL,
	customerPhone VARCHAR(20),
	CONSTRAINT VehicleCustomerFK
		FOREIGN KEY (customerPhone) REFERENCES Customer(phoneNo)
		ON DELETE SET NULL,
	)  

GO


CREATE TABLE dbo.Sale (
	plateNo VARCHAR(15) NOT NULL,
	[date] datetime NOT NULL, 
	[description] VARCHAR(500),
	PRIMARY KEY (plateNo, [date]),
	CONSTRAINT SaleVehicleFK
		FOREIGN KEY (plateNo) REFERENCES Vehicle(plateNumber)
		ON DELETE CASCADE,
	)  

GO

CREATE TABLE dbo.[Service] (
	id int PRIMARY KEY IDENTITY(1,1),  
	[description] VARCHAR(500) NOT NULL,
	[time] decimal(5,2) NOT NULL,
	price MONEY NOT NULL,
	plateNr VARCHAR(15) NOT NULL,
	[date] datetime NOT NULL,
	CONSTRAINT ServiceSaleFK
		FOREIGN KEY (plateNr, [date]) REFERENCES Sale(plateNo, [date])
		ON DELETE CASCADE,
	)  

GO

CREATE TABLE dbo.Product (
	id int PRIMARY KEY IDENTITY(1,1),  
	[name] varchar(15) NOT NULL,
	currentStock int NOT NULL,
	price MONEY NOT NULL,
	)

GO

CREATE TABLE dbo.OrderLine (
	id int PRIMARY KEY IDENTITY(1,1), 
	quantity int NOT NULL,
	plateNr VARCHAR(15) NOT NULL,
	[date] datetime NOT NULL,
	productId int,
	CONSTRAINT OrderLineSaleFK
		FOREIGN KEY (plateNr, [date]) REFERENCES Sale(plateNo, [date])
		ON DELETE CASCADE,
	CONSTRAINT OrderLineProductFK
		FOREIGN KEY (productId) REFERENCES Product(id)
		ON DELETE CASCADE,
	)

GO



CREATE TABLE dbo.Appointment (
	id int PRIMARY KEY IDENTITY(1,1), 
	creationDate datetime NOT NULL,
	[length] decimal(5,2) NOT NULL,
	[date] datetime NOT NULL,
	[description] VARCHAR(128) NOT NULL,
	employeePhoneNo VARCHAR(20),
	CONSTRAINT AppointmentEmployeeFK
		FOREIGN KEY (employeePhoneNo) REFERENCES Employee(phoneNo)
		ON DELETE CASCADE,
	)

GO
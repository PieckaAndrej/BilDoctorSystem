USE [CSC-CSD-S211_10407543]


ALTER TABLE dbo.Person
DROP CONSTRAINT if exists PersonCityFK;
ALTER TABLE dbo.Employee
DROP CONSTRAINT if exists EmployeePersonFK;
ALTER TABLE dbo.Customer
DROP CONSTRAINT if exists CustomerPersonFK, CustomerDiscountFK;
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
drop table if exists dbo.Discount;
drop table if exists dbo.Vehicle;
drop table if exists dbo.Sale;
drop table if exists dbo.[Service];
drop table if exists dbo.Product;
drop table if exists dbo.OrderLine;
drop table if exists dbo.Appointment;



CREATE TABLE dbo.City (
	zipcode VARCHAR(5) NOT NULL,
	countryCode VARCHAR(5) NOT NULL,
	city VARCHAR(25) NOT NULL,
	PRIMARY KEY(zipcode, countryCode),
	)  
GO


CREATE TABLE dbo.Person ( 
	[name] VARCHAR(25) NOT NULL,
	surname VARCHAR(25) NOT NULL,
	zipcode VARCHAR(5),
	[address] VARCHAR(50) NOT NULL,
	association VARCHAR(1) NOT NULL,
	phoneNumber VARCHAR(20) NOT NULL,
	countryCode VARCHAR(5) NOT NULL,
	PRIMARY KEY(phoneNumber, countryCode),
	CONSTRAINT PersonCityFK
		FOREIGN KEY (zipcode, countryCode) REFERENCES City(zipcode, countryCode)
		ON DELETE CASCADE,
	)  

GO


CREATE TABLE dbo.Employee (
   cpr VARCHAR(10)  PRIMARY KEY,
   salary MONEY NOT NULL,
   phoneNo VARCHAR(20) NOT NULL,
   countryCode VARCHAR(5) NOT NULL,
   position VARCHAR(20) NOT NULL,
   CONSTRAINT EmployeePersonFK
		FOREIGN KEY (phoneNo, countryCode) REFERENCES Person(phoneNumber, countryCode)
		ON DELETE CASCADE,
	)  

GO

CREATE TABLE dbo.Discount (
   category VARCHAR(20) PRIMARY KEY,
   [value] int NOT NULL,
   [date] datetime NOT NULL,
	)  

GO

CREATE TABLE dbo.Customer (
   discountCategory VARCHAR(20) NOT NULL,
   phoneNo VARCHAR(20) NOT NULL,
   countryCode VARCHAR(5) NOT NULL,
   PRIMARY KEY(phoneNo, countryCode),
   CONSTRAINT CustomerPersonFK
		FOREIGN KEY (phoneNo, countryCode) REFERENCES Person(phoneNumber, countryCode)
		ON DELETE CASCADE,
   CONSTRAINT CustomerDiscountFK
		FOREIGN KEY (discountCategory) REFERENCES Discount(category)
		ON DELETE CASCADE,
	)  

GO

CREATE TABLE dbo.Vehicle (
	plateNumber VARCHAR(15) PRIMARY KEY NOT NULL,
	[year] int NOT NULL,
	brand VARCHAR(20) NOT NULL,
	customerPhone VARCHAR(20),
	countryCode VARCHAR(5) NOT NULL,
	CONSTRAINT VehicleCustomerFK
		FOREIGN KEY (customerPhone, countryCode) REFERENCES Customer(phoneNo, countryCode)
		ON DELETE CASCADE,
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
	saleDate datetime NOT NULL,
	[length] int NOT NULL,
	[date] datetime NOT NULL,
	[description] VARCHAR(128) NOT NULL,
	employeeCpr VARCHAR(10) NOT NULL,
	CONSTRAINT AppointmentEmployeeFK
		FOREIGN KEY (employeeCpr) REFERENCES Employee(cpr)
		ON DELETE CASCADE,
	)

GO
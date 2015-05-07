/*
SELECT * FROM Customers;
SELECT * FROM CustomerContacts;
*/

/*******************************
 *  Database - eCeepFramework
 *******************************/
USE eCeepFramework; 

/*******************************
 *  DROP TABLEs
 *******************************/
DROP TABLE IF EXISTS CustomerContacts;
DROP TABLE IF EXISTS Customers;

/******************************
 *  Table - Customers
 ******************************/
CREATE TABLE Customers(
	ID						INT					NOT NULL	AUTO_INCREMENT,
	CustomerName			NVARCHAR(255)		NULL,
	Street					NVARCHAR(255)		NULL,
	City					NVARCHAR(255)		NULL,
	State					NVARCHAR(255)		NULL,
	Country					NVARCHAR(255)		NULL,
	PostalCode				NVARCHAR(255)		NULL,
	PhoneNo					NVARCHAR(255)		NULL,
	FaxNo					NVARCHAR(255)		NULL,
	Notes					NVARCHAR(255)		NULL,
	ParentID				INT					NULL,
	AgentID					INT					NULL,
	CreatedByID				INT					NULL,
	CreatedByName			NVARCHAR(255)		NULL,
	CreatedTime				DATETIME			NULL		DEFAULT NOW(),
	ModifiedByID			INT					NULL,
	ModifiedByName			NVARCHAR(255)		NULL,
	ModifiedTime			DATETIME			NULL		DEFAULT NOW(),
	IsDeleted				BIT					NOT NULL	DEFAULT FALSE,
    DeletedByID				INT					NULL,
    DeletedByName			NVARCHAR(255)		NULL,
    DetetedTime				DateTime			NULL,
    
	PRIMARY KEY(ID)
);
INSERT INTO Customers(ID,CustomerName,CreatedByID,CreatedByName,ModifiedByID,ModifiedByName) VALUES(1,'Customer1',1,'Admin',1,'Admin');
INSERT INTO Customers(ID,CustomerName,CreatedByID,CreatedByName,ModifiedByID,ModifiedByName) VALUES(2,'Customer2',1,'Admin',1,'Admin');
INSERT INTO Customers(ID,CustomerName,CreatedByID,CreatedByName,ModifiedByID,ModifiedByName) VALUES(3,'Customer3',1,'Admin',1,'Admin');

/******************************
 *  Table - CustomerContacts
 ******************************/
CREATE TABLE CustomerContacts(
	ID 						INT 				NOT NULL	AUTO_INCREMENT,
	CustomerID 				INT 				NULL		REFERENCES Customers(ID),
    CustomerName			NVARCHAR(255)		NULL,
	ContactName 			NVARCHAR(255) 		NULL,
	IsPrimaryContact 		BIT 				NULL,
	ContactTitle 			NVARCHAR(255) 		NULL,
	DirectPhoneNo 			NVARCHAR(255) 		NULL,
	DirectFaxNo 			NVARCHAR(255) 		NULL,
	EmailAddress 			NVARCHAR(255) 		NULL,
	Note 					NVARCHAR(255)		NULL,
	CreatedByID				INT					NULL,
	CreatedByName			NVARCHAR(255)		NULL,
	CreatedTime				DATETIME			NULL		DEFAULT NOW(),
	IsDeleted 				BIT 				NOT NULL 	DEFAULT FALSE,
    DeletedByID				INT					NULL,
    DeletedByName			NVARCHAR(255)		NULL,
    DetetedTime				DateTime			NULL,
	
 	PRIMARY KEY(ID)
);
INSERT INTO CustomerContacts(ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,CreatedByID,CreatedByName) VALUES(1,1,'Customer1','Contact11',TRUE,1,'Admin');
INSERT INTO CustomerContacts(ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,CreatedByID,CreatedByName) VALUES(2,1,'Customer1','Contact12',FALSE,1,'Admin');

INSERT INTO CustomerContacts(ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,CreatedByID,CreatedByName) VALUES(3,2,'Customer2','Contact21',FALSE,1,'Admin');
INSERT INTO CustomerContacts(ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,CreatedByID,CreatedByName) VALUES(4,2,'Customer2','Contact22',TRUE,1,'Admin');

INSERT INTO CustomerContacts(ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,CreatedByID,CreatedByName) VALUES(5,3,'Customer3','Contact3',TRUE,1,'Admin');


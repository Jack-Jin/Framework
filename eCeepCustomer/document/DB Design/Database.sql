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
DROP TABLE IF EXISTS CustomerActivities;
DROP TABLE IF EXISTS L_ActivityType;

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

/******************************
 *  Table - L_ActivityType
 ******************************/
CREATE TABLE L_ActivityType(
	ID						int					NOT NULL	AUTO_INCREMENT,
    ActivityType			NVARCHAR(255)		NULL,
    PRIMARY KEY(ID)
);
INSERT INTO L_ActivityType(ID,ActivityType) VALUES(1,'Visit Customer');
INSERT INTO L_ActivityType(ID,ActivityType) VALUES(2,'Customer Dropby');
INSERT INTO L_ActivityType(ID,ActivityType) VALUES(3,'Telephone');
INSERT INTO L_ActivityType(ID,ActivityType) VALUES(4,'eMail');
INSERT INTO L_ActivityType(ID,ActivityType) VALUES(5,'Other Type');

/******************************
 *  Table - CustomerActivities
 ******************************/
CREATE TABLE CustomerActivities(
	ID 						INT 				NOT NULL	AUTO_INCREMENT,
	CustomerID 				INT 				NULL		REFERENCES Customers(ID),
    CustomerName			NVARCHAR(255)		NULL,
    Activity				NVARCHAR(255)		NULL,
    ActivityTypeID			INT					NULL		REFERENCES L_ActivityType(ID),
    ActivityType			NVARCHAR(255)		NULL,    
	Detail 					NVARCHAR(255) 		NULL,
    StartTime				DATETIME			NULL		DEFAULT NOW(),
    EndTime					DATETIME			NULL		DEFAULT NOW(),    
	ClosedByID				INT					NULL,
	ClosedByName			NVARCHAR(255)		NULL,
	ClosedTime				DATETIME			NULL		DEFAULT NOW(),
	CreatedByID				INT					NULL,
	CreatedByName			NVARCHAR(255)		NULL,
	CreatedTime				DATETIME			NULL		DEFAULT NOW(),
	IsDeleted 				BIT 				NOT NULL 	DEFAULT FALSE,
    DeletedByID				INT					NULL,
    DeletedByName			NVARCHAR(255)		NULL,
    DetetedTime				DateTime			NULL,
	
 	PRIMARY KEY(ID)
);
INSERT INTO CustomerActivities(ID,CustomerID,CustomerName,Activity,ActivityTypeID,ActivityType,Detail) VALUES(1,1,'Customer1','Activity1',1,'Visit Customer','Detail1');

#DELIMITER //

/*******************************
 *  Database - eCeepFramework
 *******************************/
CREATE DATABASE eCeepFramework;

USE eCeepFramework;

/**********************
 *  Table - Users
 **********************/
DROP TABLE Users;
CREATE TABLE Users (
    ID	                INT             NOT NULL				AUTO_INCREMENT,
	UsersName           VARCHAR(255)    NOT NULL,
	Password            VARCHAR(255)    NOT NULL,

	FirstName           VARCHAR(255)    NULL,
	LastName            VARCHAR(255)    NULL,
	Title               VARCHAR(255)    NULL,
	Address             VARCHAR(255)    NULL,
	Address1            VARCHAR(255)    NULL,
	City                VARCHAR(255)    NULL,
	State               VARCHAR(255)    NULL,
	Country             VARCHAR(255)    NULL,
	PostalCode          VARCHAR(255)    NULL,
	Telephone           VARCHAR(255)    NULL,
	Fax	                VARCHAR(255)    NULL,
	EMail	            VARCHAR(255)    NULL,
	WWW                 VARCHAR(255)    NULL,
	Note                VARCHAR(255)    NULL,

    CompanyID           INTEGER         NOT NULL,
	Company             VARCHAR(255)    NOT NULL,
	PolicyID            INTEGER         NOT NULL,
	Policy              VARCHAR(255)    NOT NULL,

	CurrencyID          INTEGER         NULL,
	Currency            VARCHAR(255)    NULL,
	UnitID              INTEGER         NULL,
	Unit                VARCHAR(255)    NULL,
	LanguageID          INTEGER         NULL,
	Language            VARCHAR(255)    NULL,

	IsSuperAdmin        BIT             NOT NULL DEFAULT FALSE,
	IsNeverExpire       BIT             NOT NULL DEFAULT FALSE,
	ExpiryDate          DATETIME        NOT NULL DEFAULT NOW(),
	CreateBy            INTEGER         NULL,
	CreateDate          DATETIME        NOT NULL DEFAULT NOW(),

    IsAccountOnHold     BIT             NOT NULL DEFAULT FALSE,
	IsDeleted           BIT             NOT NULL DEFAULT FALSE,
	DeletedBy           VARCHAR(255)    NULL,
	DeletedDate         DATETIME        NULL,

	LoginTime           DATETIME        NULL,
	LogoutTime          DATETIME        NULL,

    PRIMARY KEY(ID)
);

/******************************
 *  Table - UserLoginHistory
 ******************************/
DROP TABLE UserLoginHistory;
CREATE TABLE UserLoginHistory (
	ID                  INT            NOT NULL 			   	AUTO_INCREMENT,
	UsersID             INT            NOT NULL,
	UsersName           VARCHAR(255)   NULL,	
	LoginTime           DATETIME       NULL,
	LogoutTime          DATETIME       NULL,
	ExpireTime          DATETIME       NULL,
	IP	                VARCHAR(255)   NULL,
	OSInfo	            VARCHAR(255)   NULL,
	SessionID           VARCHAR(255)   NULL,
	
	PRIMARY KEY(ID)
);

/******************************
 *  Table - UserCompany
 ******************************/
DROP TABLE UserCompany;
CREATE TABLE UserCompany (
	ID                  INT             NOT NULL                  AUTO_INCREMENT,
	CompanyName         VARCHAR(255)    NOT NULL,
	Address             VARCHAR(255)    NULL,
	Address1            VARCHAR(255)    NULL,
	City                VARCHAR(255)    NULL,
	State               VARCHAR(255)    NULL,
	Country             VARCHAR(255)    NULL,
	PostalCode          VARCHAR(255)    NULL,
	Telephone           VARCHAR(255)    NULL,
	Fax	                VARCHAR(255)    NULL,
	EMail	            VARCHAR(255)    NULL,
	WWW                 VARCHAR(255)    NULL,
	ParentID            INT             NULL,
	ParentName          VARCHAR(255)    NULL,
	ContactID           INT             NULL,
	ContactName         VARCHAR(255)    NULL,
	PolicyID            INT             NULL, 
	Policy              VARCHAR(255)    NULL,
	IsSystem            BIT             NOT NULL DEFAULT FALSE,
	
	PRIMARY KEY(ID)
);

INSERT INTO UserCompany(CompanyName,ParentID,IsSystem) VALUES('__System Super',-1,TRUE)
INSERT INTO UserCompany(CompanyName,ParentID,IsSystem) VALUES('__System Default',-1,TRUE)

/******************************
 *  Table - PolicyRule
 ******************************/
DROP TABLE PolicyRule;
CREATE TABLE PolicyRule (
	ID                 	INT             NOT NULL                 	AUTO_INCREMENT,
	RuleName	        VARCHAR(255)    NOT NULL,
	RuleValue	        VARCHAR(255)    NOT NULL,
	ValueType	        VARCHAR(255)    NOT NULL,
	DisplayOrder	    INT    			NOT NULL        DEFAULT 0,
	IsSystemRule        BIT             NOT NULL 		DEFAULT True,
)

/******************************
 *  Table - Policy
 ******************************/
DROP TABLE Policy;
CREATE TABLE Policy (
	ID                  INT             NOT NULL                 	AUTO_INCREMENT, 	
	PolicyName          VARCHAR(255)    NOT NULL,
	Description   		VARCHAR(255)    NULL,	
	Menus               VARCHAR(255)    NOT NULL,
	CompanyID           INT             NULL,
	
	PRIMARY KEY(ID)
);

/******************************
 *  Table - PolicyDetail
 ******************************/
DROP TABLE PolicyDetail;
CREATE TABLE PolicyDetail (
	ID                  INT             NOT NULL                 	AUTO_INCREMENT, 	
	PolicyID            INT             NOT NULL,
	PolicyName          VARCHAR(255)    NOT NULL,
	PolicyRuleID 	    INT    			NOT NULL,
	PolicyRuleName      VARCHAR(255)    NOT NULL,
	
	PRIMARY KEY(ID)
);

/******************************
 *  Table - Menu
 ******************************/
DROP TABLE Menu;
CREATE TABLE Menu (
	ID                  INT             NOT NULL,
	ParentID            INT             NOT NULL,
	MenuText            VARCHAR(255)    NOT NULL,
	IsLeaf	            BIT             NOT NULL DEFAULT TRUE,
	MenuNo	            VARCHAR(255)    NOT NULL,
	URL	                VARCHAR(255)    NULL,
	
	PRIMARY KEY(ID)
)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(10, 0 , 'Quotations'		, FALSE, '010' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(11, 10, 'New Quote'			, TRUE, '011' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(12, 10, 'Quote List'		, TRUE, '012' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(13, 10, 'Save'				, TRUE	, '013' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(14, 10, 'Save as New Quote'	, TRUE, '014' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(15, 10, 'Save as New Rev'	, TRUE, '015' ,NULL)

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(20, 0 , 'User Admin'		, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(21, 20, 'Company Admin'		, FALSE, '' ,NULL)

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(30, 0 , 'Data Control'		, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(31, 30, 'Data Update'		, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(32, 30, 'Post News'			, FALSE, '' ,NULL)

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(40, 0 , 'User Support'		, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(41, 40, 'Info Center'		, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(42, 40, 'My Profile'		, FALSE, '' ,NULL)

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(50, 0 , 'CRM'				, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(51, 50, 'Customer'			, FALSE, '' ,NULL)

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(60, 0 , 'Management Report'	, FALSE, '' ,NULL)
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(61, 60, 'Deleted Quotes'	, FALSE, '' ,NULL)

PKID		ParentID	Description			IsLeaf	PageUrl
1			0         	 					0		NULL

19			1         	Quotations			0		NULL
27			19        	New Quote			1		Main/NewQuote
20			19        	Quote List			1		Main/QuotationList
21			19        	Save				1		javascript: saveQuote();
22			19        	Save as New Quote	1		SaveAsNewQuote
23			19        	Save as New Rev		1		SaveAsRev

7			1         	User Admin			0		NULL
34			7         	Company Admin		1		UserAdmin/CompanyAdmin.aspx

10			1         	Data Control		0		NULL
11			10        	Data Update			1		Main/DataUpdate
12			10        	Post News			1		News/NewsDetail.aspx?id=0

24			1         	User Support		0		NULL
15			24        	Info Center			1		Main/InfoCenter
16			24        	My Profile			1		UserAdmin/MyProfile.aspx

25			1         	CRM					0		NULL
26			25        	Customer			1		Customer/SearchCustomerNew.aspx

29			1         	Management Report	0		NULL
30			29        	Deleted Quotes		1		~/Quotation/QuotationListDeleted.aspx







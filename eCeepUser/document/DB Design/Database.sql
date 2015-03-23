#DELIMITER //

SELECT * FROM Menu ORDER BY MenuNo;
SELECT * FROM PolicyRule ORDER BY DisplayOrder;
SELECT * FROM Policy;
SELECT * FROM PolicyDetail ORDER BY PolicyID,PolicyRuleID;
SELECT * FROM UserCompany;
SELECT * FROM Users;
SELECT * FROM UserLoginhistory;

/*******************************
 *  Database - eCeepFramework
 *******************************/
CREATE DATABASE eCeepFramework;

USE eCeepFramework;

#DROP TABLE Menu;
#DROP TABLE PolicyRule;
#DROP TABLE Policy;
#DROP TABLE PolicyDetail;
#DROP TABLE UserCompany;
#DROP TABLE Users;
#DROP TABLE UserLoginHistory;

/******************************
 *  Table - Menu
 ******************************/
CREATE TABLE Menu (
	ID                  INT             NOT NULL 		AUTO_INCREMENT,
	ParentID            INT             NOT NULL,
	MenuText            VARCHAR(255)    NOT NULL,
	IsLeaf	            BIT             NOT NULL		DEFAULT TRUE,
	MenuNo	            VARCHAR(255)    NOT NULL,
	URL	                VARCHAR(255)    NULL,
	
	PRIMARY KEY(ID)
);
-- 10,11,12,13,14,15,  20,21,  30,31,32,  40,41,42,  50,51,  60,61
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(10,0, 'Quotations',     	FALSE,'010',NULL);
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(11,10,'New Quote', 			TRUE, '011','Main/NewQuote');
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(12,10,'Quote List', 		TRUE, '012','Main/QuotationList');
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(13,10,'Save', 				TRUE, '013','javascript: saveQuote();');
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(14,10,'Save as New Quote', 	TRUE, '014','SaveAsNewQuote');
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(15,10,'Save as New Rev', 	TRUE, '015','SaveAsRev');

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(20,0, 'User Admin', 		FALSE,'020',NULL);
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(21,20,'Company Admin', 		FALSE,'021','UserAdmin/CompanyAdmin.aspx');

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(30,0, 'Data Control', 		FALSE,'030',NULL);
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(31,30,'Data Update',		FALSE,'031','Main/DataUpdate');
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(32,30,'Post News', 			FALSE,'032','News/NewsDetail.aspx?id=0');

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(40,0, 'User Support', 		FALSE,'040',NULL);
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(41,40,'Info Center', 		FALSE,'041','Main/InfoCenter');
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(42,40,'My Profile', 		FALSE,'042','UserAdmin/MyProfile.aspx');

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(50,0, 'CRM', 				FALSE,'050',NULL);
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(51,50,'Customer', 			FALSE,'051','Customer/SearchCustomerNew.aspx');

INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(60,0, 'Management Report', 	FALSE,'060',NULL);
INSERT INTO Menu(ID,ParentID,MenuText,IsLeaf,MenuNo,URL) VALUES(61,60,'Deleted Quotes', 	FALSE,'061','~/Quotation/QuotationListDeleted.aspx');

/******************************
 *  Table - PolicyRule
 ******************************/
CREATE TABLE PolicyRule (
	ID                 	INT             NOT NULL 		AUTO_INCREMENT,
	RuleName	        VARCHAR(255)    NOT NULL,
	ValueType	        VARCHAR(255)    NOT NULL,
	RuleValue	        VARCHAR(255)    NOT NULL,
	IsSystemRule        BIT             NOT NULL 		DEFAULT True,
	DisplayOrder	    INT    			NOT NULL        DEFAULT 0,
	
	PRIMARY KEY(ID)
);
INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(20,'Access All Customer','Option','True',FALSE,20);
INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(21,'Show All Quotes','Option','True',FALSE,21);

INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(101,'ModelList','Option','1',FALSE,101);
INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(102,'ModelList','Option','2',FALSE,102);
INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(103,'ModelList','Option','3',FALSE,103);
INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(104,'ModelList','Option','4',FALSE,104);
INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(105,'ModelList','Option','5',FALSE,105);

INSERT INTO PolicyRule(ID,RuleName,ValueType,RuleValue,IsSystemRule,DisplayOrder) VALUES(500,'Factor','value','0.43',FALSE,500);

/******************************
 *  Table - Policy
 ******************************/
CREATE TABLE Policy (
	ID                  INT             NOT NULL    	AUTO_INCREMENT, 
	PolicyName          VARCHAR(255)    NOT NULL,
	Description   		VARCHAR(255)    NULL,
	Menus               VARCHAR(255)    NOT NULL,
	CompanyID           INT             NULL,
	
	PRIMARY KEY(ID)
);
INSERT INTO Policy(ID,PolicyName,Description,Menus) VALUES(1,'System Super Policy','This policy for super admin users.','10,11,12,13,14,15,20,21,30,31,32,40,41,42,50,51,60,61');
INSERT INTO Policy(ID,PolicyName,Description,Menus) VALUES(2,'System Default Policy','This is the system default policy','10,11,12,13,14,15,20,21,30,31,32,40,41,42,50,51,60,61');

/******************************
 *  Table - PolicyDetail
 ******************************/
CREATE TABLE PolicyDetail (
	ID                  INT             NOT NULL       	AUTO_INCREMENT, 	
	PolicyID            INT             NOT NULL,
	PolicyName          VARCHAR(255)    NOT NULL,
	PolicyRuleID 	    INT    			NOT NULL,
	PolicyRuleName      VARCHAR(255)    NOT NULL,
	ValueType	        VARCHAR(255)    NOT NULL,
	RuleValue	        VARCHAR(255)    NOT NULL,
	
	PRIMARY KEY(ID)
);
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',20,'Access All Customer','Option','True');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',21,'Show All Quotes','Option','True');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',101,'ModelList','Option','1');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',102,'ModelList','Option','2');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',103,'ModelList','Option','3');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',104,'ModelList','Option','4');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',105,'ModelList','Option','5');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(1,'System Super Policy',500,'Factor','value','0.43');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',20,'Access All Customer','Option','True');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',21,'Show All Quotes','Option','True');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',101,'ModelList','Option','1');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',102,'ModelList','Option','2');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',103,'ModelList','Option','3');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',104,'ModelList','Option','4');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',105,'ModelList','Option','5');
INSERT INTO PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue) VALUES(2,'System Default Policy',500,'Factor','value','0.43');

/******************************
 *  Table - UserCompany
 ******************************/
CREATE TABLE UserCompany (
	ID                  INT             NOT NULL 		AUTO_INCREMENT,
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
	IsSystem            BIT             NOT NULL 		DEFAULT FALSE,
	
	PRIMARY KEY(ID)
);
INSERT INTO UserCompany(ID,CompanyName,ParentID,IsSystem) VALUES(1,'__System Super',0,TRUE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,IsSystem) VALUES(2,'__System Default',0,TRUE);

/**********************
 *  Table - Users
 **********************/
CREATE TABLE Users (
    ID	                INT             NOT NULL 		AUTO_INCREMENT,
	UserName            VARCHAR(255)    NOT NULL,
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
	PolicyID            INTEGER         NULL,
	Policy              VARCHAR(255)    NULL,

	CurrencyID          INTEGER         NULL,
	Currency            VARCHAR(255)    NULL,
	UnitID              INTEGER         NULL,
	Unit                VARCHAR(255)    NULL,
	LanguageID          INTEGER         NULL,
	Language            VARCHAR(255)    NULL,

	IsSuperAdmin        BIT             NOT NULL 		DEFAULT FALSE,
	IsNeverExpire       BIT             NOT NULL 		DEFAULT FALSE,
	ExpiryDate          DATETIME        NOT NULL 		DEFAULT NOW(),
	CreateByID          INTEGER         NULL,
	CreateBy            VARCHAR(255)	NULL,
	CreateDate          DATETIME        NOT NULL 		DEFAULT NOW(),

    IsAccountOnHold     BIT             NOT NULL 		DEFAULT FALSE,
	IsDeleted           BIT             NOT NULL 		DEFAULT FALSE,
	DeleteByID          INTEGER  		NULL,
	DeletedBy           VARCHAR(255)    NULL,
	DeletedDate         DATETIME        NULL,

	LoginTime           DATETIME        NULL,
	LogoutTime          DATETIME        NULL,

    PRIMARY KEY(ID)
);
INSERT INTO Users(ID,UserName,Password,FirstName,LastName,Country,CompanyID,Company,PolicyID,Policy,CurrencyID,Currency,UnitID,Unit
                 ,LanguageID,Language,IsSuperAdmin,IsNeverExpire,CreateByID,CreateBy)
VALUES(1,'test user','peter__123','Test','User','Canada',1,'__System Super',NULL,NULL,1,'CAD',1,'SI'
      ,1,'en-CA',TRUE,TRUE,1,'test user');

/******************************
 *  Table - UserLoginHistory
 ******************************/
CREATE TABLE UserLoginHistory (
	ID                  INT            NOT NULL 		AUTO_INCREMENT,
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

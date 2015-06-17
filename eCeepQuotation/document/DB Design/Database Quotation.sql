/*
SELECT * FROM News;
*/

/*******************************
 *  Database - eCeepFramework
 *******************************/
USE eCeepFramework; 

/*******************************
 *  DROP TABLEs
 *******************************/
DROP TABLE IF EXISTS QuotationItem;
DROP TABLE IF EXISTS Quotation;
DROP TABLE IF EXISTS QuotationNumber;

/******************************
 *  Table - Quotation
 ******************************/
CREATE TABLE Quotation
(
#============================= Quotation INFO =================================
	ID					        INT				NOT NULL	AUTO_INCREMENT,
	QuotationNo					NVARCHAR(100)	NULL,
	QuotationProjectName		NVARCHAR(100)	NULL,
	QuotationReference			NVARCHAR(100)	NULL,
	QuotationNote				NVARCHAR(2000)	NULL,
	QuotationLocation			NVARCHAR(200)	NULL,
	QuotationUnit				INT				NULL        DEFAULT 0,
	QuotationCurrency			INT				NULL        DEFAULT 0,
#============================= Cost & Pricing =================================
	QuotationCost				DECIMAL(13,2)	NULL,
	QuotationPrice				DECIMAL(13,2)	NULL,
#============================= Quote Status, Type & SalesType =================
	QuotationStatus				NVARCHAR(50)	NULL,
	QuotationType				NVARCHAR(20)	NULL,
	QuotationSalesType			NVARCHAR(50)	NULL,
	QuotationExtension          LONGBLOB        NULL,
	MilestoneBinary             LONGBLOB        NULL,
	QuotationCurrentItemID      NVARCHAR(255)   NULL        DEFAULT '',
#============================= Datetime & UserID ==============================
	QuotationByUserID			INT				NULL,
	QuotationByUserName         NVARCHAR(255)   NULL,
	QuotationDate				DATETIME		NULL		DEFAULT NOW(),
	QuotationModifiedByUserID	INT				NULL,
	QuotationModifiedByUserName NVARCHAR(255)   NULL,
	QuotationModifiedDate		DATETIME		NULL		DEFAULT NOW(),
	QuotationDeletedByUserID	INT				NULL,
	QuotationDeletedByUserName  NVARCHAR(255)   NULL,
	QuotationDeletedDate		DATETIME		NULL,
	IsDeleted					BIT				NOT NULL	DEFAULT FALSE,

	PRIMARY KEY(ID)
);

create table QuotationItem
(
	ID					        NVARCHAR(255)	NOT NULL,
	QuotationID					INT				NOT NULL,
	Sequence                    INT             NOT NULL    DEFAULT 0,
	Item				        NVARCHAR(255)	NOT NULL	DEFAULT '',
	ItemRevision				NVARCHAR(50)    NULL,
	ItemUnitSystem				INT				NULL		DEFAULT 0,
	ItemCurrency                INT             NULL        DEFAULT 0,
	ItemProductType				INT				NOT NULL    DEFAULT 1,
	ItemIndustryType            INT             NULL        DEFAULT 1,
	ItemProductApplicationType	INT				NULL		DEFAULT 0,
	ItemCost			        DECIMAL(13,2)	NULL,
	ItemPrice			        DECIMAL(13,2)	NULL,
	ItemExtension               LONGBLOB        NULL,
	SerializationType           NVARCHAR(255)   NOT NULL,
	SerializationBinary			LONGBLOB		NULL,
	ItemDate                    DATETIME        NULL        DEFAULT NOW(),
	ItemModifiedDate            DATETIME        NULL        DEFAULT NOW(),

	PRIMARY KEY(ID),
	FOREIGN KEY(QuotationID) REFERENCES Quotation(ID)
);

CREATE TABLE QuotationNumber
(
	CurrentNumber               INT         	NOT NULL  	DEFAULT 1
);


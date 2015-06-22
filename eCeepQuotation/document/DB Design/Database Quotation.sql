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
	QuotationNo					NVARCHAR(100)	NULL		DEFAULT '',
	QuotationProjectName		NVARCHAR(100)	NULL		DEFAULT '',
	QuotationReference			NVARCHAR(100)	NULL		DEFAULT '',
	QuotationNote				NVARCHAR(2000)	NULL		DEFAULT '',
	QuotationLocation			NVARCHAR(200)	NULL		DEFAULT '',
    
	UnitID						INT				NULL        DEFAULT -1,
	CurrencyID					INT				NULL        DEFAULT -1,

	QuotationBinary          	LONGBLOB        NULL,
	MilestoneBinary             LONGBLOB        NULL,
	QuotationItemsCurrentID		NVARCHAR(255)   NULL        DEFAULT '',

	Cost						DECIMAL(13,2)	NULL    	DEFAULT 0,
	Price						DECIMAL(13,2)	NULL    	DEFAULT 0,
	`Status`					INT				NULL		DEFAULT 0,
	`Type`						NVARCHAR(20)	NULL		DEFAULT '',
	SalesType					NVARCHAR(50)	NULL		DEFAULT '',
    
	#============================= Datetime & UserID ==============================
	CreatedByID					INT				NULL        DEFAULT -1,
	CreatedByName				NVARCHAR(255)	NULL		DEFAULT '',
	CreatedTime					DATETIME		NULL		DEFAULT NOW(),

	ModifiedByID				INT				NULL        DEFAULT -1,
	ModifiedByName				NVARCHAR(255)	NULL		DEFAULT '',
	ModifiedTime				DATETIME		NULL		DEFAULT NOW(),

	IsDeleted					BIT				NOT NULL	DEFAULT FALSE,
    DeletedByID					INT				NULL		DEFAULT -1,
    DeletedByName				NVARCHAR(255)	NULL		DEFAULT '',
    DetetedTime					DateTime		NULL,

	PRIMARY KEY(ID)
);

CREATE TABLE QuotationItem
(
	ID					        NVARCHAR(255)	NOT NULL,
	QuotationID					INT				NOT NULL,
	Sequence                    INT             NOT NULL    DEFAULT 0,
	ItemName			        NVARCHAR(255)	NOT NULL	DEFAULT '',
	ItemRevision				NVARCHAR(50)    NULL		DEFAULT '',
	
    UnitID						INT				NULL        DEFAULT -1,
	CurrencyID                	INT             NULL        DEFAULT -1,
	ProductTypeID				INT				NOT NULL    DEFAULT 1,
	IndustryTypeID            	INT             NULL        DEFAULT 1,
	ProductApplicationTypeID	INT				NULL		DEFAULT 0,
    
	Cost			        	DECIMAL(13,2)	NULL    	DEFAULT 0,
	Price			        	DECIMAL(13,2)	NULL    	DEFAULT 0,
    
	ItemBinaryType           	NVARCHAR(255)   NOT NULL,
	ItemBinary					LONGBLOB		NULL,

	CreatedByID					INT				NULL        DEFAULT -1,
	CreatedByName				NVARCHAR(255)	NULL		DEFAULT '',
	CreatedTime					DATETIME		NULL		DEFAULT NOW(),

	ModifiedByID				INT				NULL        DEFAULT -1,
	ModifiedByName				NVARCHAR(255)	NULL		DEFAULT '',
	ModifiedTime				DATETIME		NULL		DEFAULT NOW(),

	PRIMARY KEY(ID),
	FOREIGN KEY(QuotationID) REFERENCES Quotation(ID)
);

CREATE TABLE QuotationNumber
(
	ID							INT 			NOT NULL,
	CurrentNumber               INT         	NOT NULL  	DEFAULT 0,
    PRIMARY KEY(ID)
);
INSERT INTO QuotationNumber(ID) VALUES(1);

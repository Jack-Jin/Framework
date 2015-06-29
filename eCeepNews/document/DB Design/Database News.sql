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
DROP TABLE IF EXISTS News;

/******************************
 *  Table - News
 ******************************/
CREATE TABLE News(
	ID						INT					NOT NULL	AUTO_INCREMENT,
    Title					NVARCHAR(255)		NULL,
    Content					TEXT				NULL,
    Active					BIT					NULL		DEFAULT FALSE,   
	CreatedByID				INT					NULL,
	CreatedByName			NVARCHAR(255)		NULL,
	CreatedTime				DATETIME			NULL		DEFAULT NOW(),
	ModifiedByID			INT					NULL,
	ModifiedByName			NVARCHAR(255)		NULL,
	ModifiedTime			DATETIME			NULL		DEFAULT NOW(),
    
	PRIMARY KEY(ID)
);
INSERT INTO News(ID,Title,Content,Active,CreatedByID,CreatedByName,ModifiedByID,ModifiedByName) VALUES(1,'Test News','Test News content.',TRUE,1,'Admin',1,'Admin');
INSERT INTO News(ID,Title,Content,Active,CreatedByID,CreatedByName,ModifiedByID,ModifiedByName) VALUES(2,'Test News1','Test News content1.',TRUE,1,'Admin',1,'Admin');

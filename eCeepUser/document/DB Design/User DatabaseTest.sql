/*******************************
 *  Database - eCeepFramework
 *******************************/
/* Clear Test Data

USE eCeepFramework; 

DELETE FROM Users WHERE ID IN (2,3,4,5);
DELETE FROM UserCompany WHERE ID IN (3,4,5,6,7,8,9,10,11,12,13,14,15);

*/

/******************************
 *  Test: UserCompany
 ******************************/
/*
  __System Super(1)
  __System Default(2)
  Test1(3) --------------------- Test1-1(4)
                     +---------- Test1-2(5)
                     +---------- Test1-3(6)
  Test2(7) --------------------- Test2-1(8) ---------- Test2-1-1(9)
                                                 +---- Test2-1-2(10)
                     +-----------Test2-2(11)---------- Test2-2-1(12)
                                                 +---- Test2-2-2(13)
                                                 +---- Test2-2-3(14)
  Test3(15)
*/
 
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(3 ,'Test1'    ,0 ,2,'System Default Policy',FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(4 ,'Test1-1'  ,3 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(5 ,'Test1-2'  ,3 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(6 ,'Test1-3'  ,3 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(7 ,'Test2'    ,0 ,2,'System Default Policy',FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(8 ,'Test2-1'  ,7 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(9 ,'Test2-1-1',8 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(10,'Test2-1-2',8 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(11,'Test2-2'  ,7 ,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(12,'Test2-2-1',11,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(13,'Test2-2-2',11,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(14,'Test2-2-3',11,null,null,FALSE);
INSERT INTO UserCompany(ID,CompanyName,ParentID,PolicyID,Policy,IsSystem) VALUES(15,'Test3'    ,0 ,2,'System Default Policy',FALSE);

SELECT ID,CompanyName,ParentID,PolicyID,Policy,IsSystem FROM UserCompany;

/******************************
 *  Test: Users
 ******************************/
INSERT INTO Users(ID,UserName,Password,FirstName,LastName,Country,CompanyID,Company,PolicyID,Policy,CurrencyID,Currency,UnitID,Unit
                 ,LanguageID,Language,IsAdmin,IsNeverExpire,CreateByID,CreateBy)
VALUES(2,'test1 user','123','Test1','User','Canada',3,'Test1',NULL,NULL,1,'CAD',1,'SI'
      ,1,'en-CA',FALSE,TRUE,1,'test user');
INSERT INTO Users(ID,UserName,Password,FirstName,LastName,Country,CompanyID,Company,PolicyID,Policy,CurrencyID,Currency,UnitID,Unit
                 ,LanguageID,Language,IsAdmin,IsNeverExpire,CreateByID,CreateBy)
VALUES(3,'test1 user1','123','Test1','User1','Canada',3,'Test1',NULL,NULL,1,'CAD',1,'SI'
      ,1,'en-CA',FALSE,TRUE,1,'test user');

INSERT INTO Users(ID,UserName,Password,FirstName,LastName,Country,CompanyID,Company,PolicyID,Policy,CurrencyID,Currency,UnitID,Unit
           ,LanguageID,Language,IsAdmin,IsNeverExpire,CreateByID,CreateBy)
VALUES(4,'Test1-1 user','123','Test1-1','User','Canada',4,'Test1-1',NULL,NULL,1,'CAD',1,'SI'
      ,1,'en-CA',FALSE,TRUE,1,'test user');
INSERT INTO Users(ID,UserName,Password,FirstName,LastName,Country,CompanyID,Company,PolicyID,Policy,CurrencyID,Currency,UnitID,Unit
                 ,LanguageID,Language,IsAdmin,IsNeverExpire,CreateByID,CreateBy)
VALUES(5,'Test1-1 user1','123','Test1-1','User1','Canada',4,'Test1-1',NULL,NULL,1,'CAD',1,'SI'
      ,1,'en-CA',FALSE,TRUE,1,'test user');
      
SELECT * FROM Users;

Show TABLES;

SELECT * FROM Menu ORDER BY MenuNo;
SELECT * FROM PolicyRule ORDER BY DisplayOrder;
SELECT * FROM Policy;
SELECT * FROM PolicyDetail ORDER BY PolicyID,PolicyRuleID;
SELECT * FROM UserCompany;
SELECT * FROM Users;
SELECT * FROM UserLoginhistory;

CALL UserLogon('test user','peter__123','1234','1.1.1.1','win7',120,@IsLogon);
SELECT @IsLogon;

UPDATE Policy SET Menus='10,11,12,13,14,15,20,21,30,31,32,40,41,42,50,51,60,61' WHERE ID=1;

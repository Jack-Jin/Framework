Show TABLES;

SELECT * FROM Menu ORDER BY MenuNo;

SELECT * FROM PolicyRule ORDER BY DisplayOrder;	
SELECT * FROM Policy;
SELECT * FROM PolicyDetail ORDER BY PolicyID,PolicyRuleID;

SELECT * FROM UserCompany;

SELECT * FROM Users;
SELECT * FROM UserLoginhistory;

/***************************************************/
CALL GetPolicy(TRUE,19);
CALL RemoveCompany(19,1);

SELECT RuleName, ValueType='Check' OR ValueType='Option' FROM PolicyRule;

SET SQL_SAFE_UPDATES=0;
UPDATE PolicyDetail set RuleValue='True' where RuleValue = '0';
SET SQL_SAFE_UPDATES=1;

CALL UserLogon('test user','peter__123','1234','1.1.1.1','win7',120,@IsLogon);
SELECT @IsLogon;

SELECT ID,CompanyName,ParentID,IFNULL(PolicyID,2)<=2 AS 'PolicyInherited'  from UserCompany;


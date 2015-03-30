DELIMITER $$

DROP PROCEDURE IF EXISTS UserLogon $$
CREATE PROCEDURE UserLogon(IN pUserName VARCHAR(255)
                          ,IN pPassword VARCHAR(255)
                          ,IN pSessionID INTEGER
                          ,IN pIp VARCHAR(255)
                          ,IN pOsInfo VARCHAR(255)
                          ,IN pSessionTimeout INTEGER
                          ,OUT pIsLogon BIT)
BEGIN
	DECLARE UserID INTEGER;
	DECLARE UserCompanyID INTEGER;
	DECLARE UserParentCompanyID INTEGER;
	DECLARE UserPolicyID INTEGER;
	
	SET pIsLogon=FALSE;

	SELECT ID,CompanyID,PolicyID INTO UserID,UserCompanyID,UserPolicyID
	FROM Users 
	WHERE IsDeleted=FALSE
	  AND IsAccountOnHold=FALSE
	  AND (IsNeverExpire=TRUE OR (IsNeverExpire=FALSE AND ExpiryDate>=NOW()))
	  AND (UserName=pUserName AND BINARY Password=pPassword);

	IF (NOT UserID IS NULL) AND UserID>0 THEN
	    # Update login time
	    UPDATE Users SET LoginTime=NOW() WHERE ID=UserID;

	    # Remember login time in history
	    INSERT UserLoginHistory (UsersID,UsersName,LoginTime,ExpireTime,IP,OSInfo,SessionID)
	    VALUES(UserID,pUserName,NOW(),DATE_ADD(NOW(), INTERVAL pSessionTimeout minute),pIp,pOsInfo,pSessionID);

		# Find user policy ID
		IF UserPolicyID IS NULL OR UserPolicyID<=0 THEN
			# Get User Company Policy ID
			SELECT PolicyID,ParentID INTO UserPolicyID,UserParentCompanyID FROM UserCompany WHERE ID=UserCompanyID;
			
			# Get User Parent Company Policy ID.
			WHILE (UserPolicyID IS NULL OR UserPolicyID<=0) AND (UserParentCompanyID>0) DO
				SELECT PolicyID,ParentID INTO UserPolicyID,UserParentCompanyID FROM UserCompany WHERE ID=UserParentCompanyID;			
			END WHILE;		
		END IF;
			    
	    # Return
	    # 1. User Info Detail.
	    SELECT ID,UserName,FirstName,LastName,Title,Address,Address1,City,State,Country,PostalCode,Telephone,Fax,Email,WWW,Note
		      ,CurrencyID,UnitID,LanguageID
		      ,IsAdmin,CreateByID,CreateDate
		      ,LoginTime,LogoutTime
		FROM Users
		WHERE ID=UserID;
		
		# 2. User Company Detail.
		SELECT ID,CompanyName,Address,Address1,City,State,Country,PostalCode,Telephone,Fax,EMail,WWW
		      ,ContactID,ContactName,IsSystem,ParentID
		FROM UserCompany
		WHERE ID=UserCompanyID;
		
		# 3. User Policy
		SELECT ID,PolicyName,Description,Menus FROM Policy WHERE ID=UserPolicyID;
		
		# 4. User Menu.
		SELECT ID,ParentID,MenuText,IsLeaf,MenuNo,URL 
		FROM Menu 
		WHERE FIND_IN_SET(ID,(SELECT Menus FROM Policy WHERE ID=UserPolicyID)) 
		ORDER BY MenuNo;
		
		# 5. User Policy Detail
		SELECT IF(ISNULL(B.PolicyID),UserPolicyID,B.PolicyID) AS 'PolicyID'
			  ,IF(ISNULL(B.PolicyName),'',B.PolicyName) AS 'PolicyName'
		      ,IF(ISNULL(B.PolicyRuleID),A.ID,B.PolicyRuleID) AS 'PolicyRuleID'
		      ,IF(ISNULL(B.PolicyRuleName),A.RuleName,B.PolicyRuleName) AS 'PolicyRuleName'
		      ,A.ValueType AS 'ValueType'
		      ,IF(ISNULL(B.RuleValue),A.RuleValue,B.RuleValue) AS 'RuleValue' 
		FROM PolicyRule A
		LEFT JOIN (SELECT PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,RuleValue FROM PolicyDetail WHERE PolicyID=UserPolicyID) B 
		ON A.ID=B.PolicyRuleID
		ORDER BY PolicyRuleID;
		
	    # Login flag.
	    SET pIsLogon=TRUE;
	END IF;

END $$

DELIMITER ;


/*
CALL UserLogon('test user','peter__123','1234','1.1.1.1','win7',120,@IsLogon);
SELECT @IsLogon;
*/

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
	
	SET pIsLogon=FALSE;

	SELECT ID,CompanyID INTO UserID,UserCompanyID
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

	    # Return
	    # 1. User Info Detail.
	    SELECT ID,UserName,FirstName,LastName,Title,Address,Address1,City,State,Country,PostalCode,Telephone,Fax,Email,WWW,Note
		      ,CurrencyID,UnitID,LanguageID
		      ,IsAdmin,IsNeverExpire,ExpiryDate
              ,CreateByID,CreateDate
		      ,LoginTime,LogoutTime
		FROM Users
		WHERE ID=UserID;
		
		# 2. User Company Detail.
		SELECT ID,CompanyName,Address,Address1,City,State,Country,PostalCode,Telephone,Fax,EMail,WWW
		      ,ContactID,ContactName,IsSystem,ParentID
		FROM UserCompany
		WHERE ID=UserCompanyID;

		# 3. User Policy
        # 4. User Menu
        # 5. User Policy Detail
        #CALL GetPolicy(FALSE,UserID);
        
	    # Login flag.
	    SET pIsLogon=TRUE;
	END IF;

END $$

DELIMITER ;


/*
CALL UserLogon('admin','1','1234','1.1.1.1','win7',120,@IsLogon);
SELECT @IsLogon;

CALL UserLogon('test user','peter__123','1234','1.1.1.1','win7',120,@IsLogon);
SELECT @IsLogon;
*/

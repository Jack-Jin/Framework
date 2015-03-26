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
	
	SET pIsLogon=FALSE;
	
	SELECT ID INTO UserID
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

	    SET pIsLogon=TRUE;
	END IF;
    
    # Return
	SELECT ID,UserName,FirstName,LastName,Title,Address,Address1,City,State,Country,PostalCode,Telephone,Fax,Email,WWW,Note
	      ,CurrencyID,UnitID,LanguageID
	      ,IsAdmin,CreateByID,CreateDate
	      ,LoginTime,LogoutTime
	FROM Users
	WHERE ID=UserID;

END $$

DELIMITER ;


/*
CALL UserLogon('test user','peter__123','1234','1.1.1.1','win7',120,@IsLogon);
SELECT @IsLogon;
*/

-- CALL UserLogon('','')

DELIMITER $$

DROP PROCEDURE IF EXISTS UserLogon $$
CREATE PROCEDURE UserLogon(IN userName VARCHAR(255)
                          ,IN password VARCHAR(255)
                          ,IN sessionID INTEGER
                          ,IN ip VARCHAR(255)
                          ,IN osInfo VARCHAR(255)
                          ,IN sessionTimeout INTEGER)
IS
BEGIN

id,userName,firstName,lastName,title,address,address1,city,state,country,postalCode,telephone,fax,
email,
www,
note,

currencyID,
unitID,
languageID,

IsAdmin,
createByID,
createDate,

loginTime,
logoutTime
	
	
	
	SELECT * FROM Users WHERE ;
	
	
	
END $$

DELIMITER ;


/*
    -- Check user account & password.
    DECLARE @userid int
    DECLARE @UserName nvarchar(50)

    SELECT @userid=UserID, @UserName=LogonID
    FROM Users
    WHERE LogonID=@logonid AND (CAST(UserPWD AS varbinary)=CAST(@pwd AS varbinary)) AND (NeverExpire=1 OR (NeverExpire<>1 and DATEDIFF(day,getDate(),ExpiryDate)>=0))

    IF isnull(@userid,0)=0  BEGIN
        Raiserror('Invalid password',18,1)
        RETURN -2
    END ELSE BEGIN
        -- Check if user account is hold on.
        IF EXISTS(select * from Users where LogonID=@logonid and (CAST(UserPWD AS varbinary)=CAST(@pwd AS varbinary)) and IsAccountOnHold=1) BEGIN
            Raiserror('User account is disabled',18,1)
            RETURN -1
        END
    END

    --* Record user logon information *
    --**************************************************************************************
    -- Update login time
    UPDATE Users SET LoginTime=getdate() WHERE UserID=@userid

    -- Remember login time in history
    INSERT UserLoginHistory (UserID,LoginTime,ExpireTime,UserName,SessionID,IP,OSInfo) VALUES(@userid, getdate(), dateadd(minute,@sessionTimeout,getdate()),@UserName, @sessionID, @ip, @osInfo)
    
    --* Return *  
    --**************************************************************************************  
    -- 1. User basic information
    SELECT * FROM vUsers WHERE UserID=@userid
 
 */

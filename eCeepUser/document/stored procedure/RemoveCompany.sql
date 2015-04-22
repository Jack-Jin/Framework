DELIMITER $$

DROP PROCEDURE IF EXISTS RemoveCompany $$
CREATE PROCEDURE RemoveCompany(IN p_CompanyID INTEGER
							  ,IN p_ByUserID INTEGER)
BEGIN
    DECLARE UserCount INTEGER;
    
    SELECT COUNT(*) INTO UserCount FROM Users WHERE CompanyID=p_CompanyID;
    
    IF UserCount>0 THEN
		UPDATE Users SET IsDeleted=TRUE
                        ,DeletedDate=NOW()
                        ,DeleteByID=p_ByUserID
                        ,DeletedBy=(SELECT UserName FROM Users WHERE ID=p_ByUserID)
		WHERE CompanyID=p_CompanyID;
        
        UPDATE UserCompany SET IsDeleted=TRUE
                              ,DeletedDate=NOW()
                              ,DeleteByID=p_ByUserID
                              ,DeletedBy=(SELECT UserName FROM Users WHERE ID=p_ByUserID)
		WHERE ID=p_CompanyID;
	ELSE
		DELETE FROM UserCompany WHERE ID=p_CompanyID;
    END IF;
        
END $$

DELIMITER ;

/*
CALL RemoveCompany(16,1);
*/

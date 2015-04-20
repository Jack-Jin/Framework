DELIMITER $$

DROP PROCEDURE IF EXISTS RemovePolicy $$
CREATE PROCEDURE RemovePolicy(IN pCompanySelected BIT
						     ,IN pID INTEGER)
BEGIN
    DECLARE CompanyParentID INTEGER;
	DECLARE p_PolicyID INTEGER;
    
    SET p_PolicyID=-1;
    
	IF NOT (pCompanySelected AND (pID=1 OR pID=2)) THEN
		IF pCompanySelected THEN
			SELECT PolicyID,ParentID INTO p_PolicyID,CompanyParentID FROM UserCompany WHERE ID=pID;
			
			IF CompanyParentID<1 THEN
				UPDATE UserCompany SET PolicyID=2, Policy='System Default Policy' WHERE ID=pID;
			ELSE
				UPDATE UserCompany SET PolicyID=NULL, Policy=NULL WHERE ID=pID;
			END IF;        
		ELSE
			SELECT PolicyID INTO p_PolicyID FROM Users WHERE ID=pID;
			
			UPDATE Users SET PolicyID=NULL, Policy=NULL WHERE ID=pID;
		END IF;
		
		IF p_PolicyID>2 THEN
			DELETE FROM PolicyDetail WHERE PolicyID=p_PolicyID;
		END IF;
    END IF;
    
END $$

DELIMITER ;

/*
CALL RemovePolicy(TRUE,5);
*/

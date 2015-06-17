DELIMITER $$

DROP PROCEDURE IF EXISTS GetQuotationNumber $$
CREATE PROCEDURE GetQuotationNumber()
BEGIN





	DECLARE UserPolicyID INTEGER;
	DECLARE UserCompanyID INTEGER;
	DECLARE UserParentCompanyID INTEGER;
    DECLARE PolicyInherited BIT;
	
	# Get UserPolicyID,UserCompanyID,UserParentCompanyID,PolicyInherited
    IF pTrueCompany_FalseUser THEN
		SELECT PolicyID,ID,ParentID,IFNULL(PolicyID,2)<=2 INTO UserPolicyID,UserCompanyID,UserParentCompanyID,PolicyInherited
        FROM UserCompany WHERE ID=pID;
	ELSE
		SELECT PolicyID,CompanyID,IFNULL(PolicyID,2)<=2 INTO UserPolicyID,UserCompanyID,PolicyInherited
        FROM Users WHERE ID=pID;
        
        IF UserPolicyID IS NULL OR UserPolicyID<=0 THEN
			# Get User Company Policy ID
			SELECT PolicyID,ParentID INTO UserPolicyID,UserParentCompanyID FROM UserCompany WHERE ID=UserCompanyID;
        END IF;
	END IF;

	# Get User Parent Company Policy ID.
	WHILE (UserPolicyID IS NULL OR UserPolicyID<=0) AND (UserParentCompanyID>0) DO
		SELECT PolicyID,ParentID INTO UserPolicyID,UserParentCompanyID FROM UserCompany WHERE ID=UserParentCompanyID;			
	END WHILE;		

    # Return
	# 1. User Policy
	SELECT ID,PolicyName,Description,Menus FROM Policy WHERE ID=UserPolicyID;
	
	# 2. User Menu.
	SELECT ID,ParentID,MenuText,IsLeaf,MenuNo,URL,FIND_IN_SET(ID,(SELECT Menus FROM Policy WHERE ID=UserPolicyID))>0 AS 'IsVisiable'
	FROM Menu 
	ORDER BY MenuNo;
	#SELECT ID,ParentID,MenuText,IsLeaf,MenuNo,URL 
	#FROM Menu 
	#WHERE FIND_IN_SET(ID,(SELECT Menus FROM Policy WHERE ID=UserPolicyID)) 
	#ORDER BY MenuNo;
	
	# 3. User Policy Detail
	SELECT IF(ISNULL(B.PolicyID),UserPolicyID,B.PolicyID) AS 'PolicyID'
		  ,IF(ISNULL(B.PolicyName),'',B.PolicyName) AS 'PolicyName'
		  ,IF(ISNULL(B.PolicyRuleID),A.ID,B.PolicyRuleID) AS 'PolicyRuleID'
		  ,IF(ISNULL(B.PolicyRuleName),A.RuleName,B.PolicyRuleName) AS 'PolicyRuleName'
          ,A.RuleOptionName
          ,A.RuleOptionValue
		  ,A.ValueType AS 'ValueType'
		  ,IF(ISNULL(B.RuleValue),A.RuleValue,B.RuleValue) AS 'RuleValue' 
	FROM PolicyRule A
	LEFT JOIN (SELECT PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,RuleValue FROM PolicyDetail WHERE PolicyID=UserPolicyID) B ON A.ID=B.PolicyRuleID
	ORDER BY DisplayOrder;
    
    # 4. PolicyInherited
    SELECT PolicyInherited AS 'PolicyInherited';
    
END $$

DELIMITER ;

/*
CALL GetPolicy(TRUE,1);
CALL GetPolicy(TRUE,3);
CALL GetPolicy(FALSE,1);
CALL GetPolicy(FALSE,2);
*/

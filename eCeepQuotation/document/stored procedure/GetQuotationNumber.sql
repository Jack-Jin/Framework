DELIMITER $$

DROP PROCEDURE IF EXISTS GetQuotationNumber $$
CREATE PROCEDURE GetQuotationNumber()
BEGIN
	DECLARE mQuotationNumber INT;
    
    SELECT CurrentNumber INTO mQuotationNumber FROM QuotationNumber WHERE ID=1;
    
    SET mQuotationNumber=mQuotationNumber+1;
    UPDATE QuotationNumber SET CurrentNumber=mQuotationNumber WHERE ID=1;

	SELECT mQuotationNumber AS 'NewQuoteNumber';
    
END $$

DELIMITER ;

/*
CALL GetQuotationNumber();
*/

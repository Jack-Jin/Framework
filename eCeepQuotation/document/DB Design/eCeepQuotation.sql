SHOW TABLES;

SELECT ID, QuotationNo, QuotationProjectName, QuotationReference, QuotationNote, QuotationLocation, UnitID, CurrencyID
     , QuotationBinary, MilestoneBinary, QuotationItemsCurrentID
     , Cost, Price, `Status`, `Type`, SalesType
     , CreatedByID, CreatedByName, CreatedTime, ModifiedByID, ModifiedByName, ModifiedTime, IsDeleted, DeletedByID, DeletedByName, DetetedTime 
FROM Quotation;

SELECT ID, QuotationID, Sequence, ItemName, ItemRevision, UnitID, CurrencyID
     , ProductTypeID, IndustryTypeID, ProductApplicationTypeID, Cost, Price
     , ItemBinaryType, ItemBinary
     , CreatedByID, CreatedByName, CreatedTime, ModifiedByID, ModifiedByName, ModifiedTime 
FROM QuotationItem;

SELECT ID,CurrentNumber FROM QuotationNumber;

#------------------------------------------------------------
#UPDATE QuotationNumber SET CurrentNumber=0 WHERE ID=1;

SELECT COUNT(*) AS 'Found' FROM Quotation;



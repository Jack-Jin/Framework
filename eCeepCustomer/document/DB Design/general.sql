Show TABLES;

SELECT * FROM Customers;
SELECT * FROM Customers WHERE IsDeleted=FALSE;

SELECT * FROM CustomerContacts;

#---------------------------------------------------------------------------
SET SQL_SAFE_UPDATES=0;
UPDATE Customers SET IsDeleted=FALSE;
UPDATE CustomerContacts SET IsDeleted=FALSE;
SET SQL_SAFE_UPDATES=1;

SELECT ContactName,IsPrimaryContact,ContactTitle,DirectPhoneNo,DirectFaxNo,EmailAddress,Note FROM CustomerContacts;


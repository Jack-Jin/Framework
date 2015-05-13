Show TABLES;

SELECT * FROM Customers;
SELECT * FROM Customers WHERE IsDeleted=FALSE;

SELECT * FROM CustomerContacts;

SELECT * FROM CustomerActivities;
SELECT ID,ActivityType FROM L_ActivityType;

#---------------------------------------------------------------------------
SET SQL_SAFE_UPDATES=0;
UPDATE Customers SET IsDeleted=FALSE;
UPDATE CustomerContacts SET IsDeleted=FALSE;
SET SQL_SAFE_UPDATES=1;

SELECT ContactName,IsPrimaryContact,ContactTitle,DirectPhoneNo,DirectFaxNo,EmailAddress,Note FROM CustomerContacts;

SELECT ID, CustomerID, CustomerName, Activity, ActivityTypeID, ActivityType, Detail, StartTime, EndTime, ClosedByID, ClosedByName, ClosedTime, CreatedByID, CreatedByName, CreatedTime, IsDeleted, DeletedByID, DeletedByName, DetetedTime FROM CustomerActivities;


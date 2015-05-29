SHOW TABLES;

SELECT ID,Title,Content,Active,CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime FROM News;

SELECT ID,Title,Content,Active,CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime FROM News ORDER BY ModifiedTime DESC

DROP TABLE IF EXISTS StockLevel ;
CREATE TABLE StockLevel (departmentName VARCHAR(255) NOT NULL, itemName VARCHAR(255) NOT NULL, itemCount INT NOT NULL, PRIMARY KEY(departmentName, itemName)) ;
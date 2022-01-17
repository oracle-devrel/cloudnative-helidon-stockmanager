DROP TABLE StockLevel ;
CREATE TABLE StockLevel (departmentName VARCHAR2(255) NOT NULL, itemName VARCHAR2(255) NOT NULL, itemCount INT NOT NULL, PRIMARY KEY(departmentName, itemName)) ;
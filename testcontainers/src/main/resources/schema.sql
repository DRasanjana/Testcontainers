DROP TABLE IF EXISTS customers;
CREATE TABLE customers(id int not null auto_increment primary key, first_name VARCHAR(255), last_name VARCHAR(255));
insert into customers (first_name, last_name) values ('John', 'Doe');
insert into customers (first_name, last_name) values ('David', 'Behler');
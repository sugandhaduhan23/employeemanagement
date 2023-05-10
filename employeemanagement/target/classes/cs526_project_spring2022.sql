DROP DATABASE IF EXISTS cs526_project_spring2022;
CREATE DATABASE cs526_project_spring2022;
USE cs526_project_spring2022;


GRANT SELECT, INSERT, DELETE, UPDATE
ON cs526_project_spring2022.*
TO 'admin'@'localhost';

-- -- Since these table will have fixed data so not creating the via JPA. 
-- -- They will only be used to populate dropdowns and map the role and department for an employee
-- create table Role(roleId int PRIMARY KEY, roleName varchar(30));

-- create table Department(departmentId int PRIMARY KEY, departmentName varchar(30));

-- insert into Role values(1, 'HR'),
--                        (2, 'Project Manager'), 
--                        (3, 'Software Engineer'), 
--                        (4, 'IT Engineer'), 
--                        (5, 'Senior Software Engineer'),
--                        (6, 'Admin');

-- insert into Department values(1, 'Applications'),
--                              (2, 'IT Support'), 
--                              (3, 'Networking'), 
--                              (4, 'Human Resources'), 
--                              (5, 'Finance and Admin');



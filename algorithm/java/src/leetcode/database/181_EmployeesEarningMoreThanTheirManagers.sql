/*
Q.181

Write an SQL query to find the employees who earn more than their managers.

Return the result table in any order.

SQL Schema
Create table If Not Exists Employee (id int, name varchar(255), salary int, managerId int)
Truncate table Employee
insert into Employee (id, name, salary, managerId) values ('1', 'Joe', '70000', '3')
insert into Employee (id, name, salary, managerId) values ('2', 'Henry', '80000', '4')
insert into Employee (id, name, salary, managerId) values ('3', 'Sam', '60000', 'None')
insert into Employee (id, name, salary, managerId) values ('4', 'Max', '90000', 'None')

+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| id          | int     |
| name        | varchar |
| salary      | int     |
| managerId   | int     |
+-------------+---------+
id is the primary key column for this table.
Each row of this table indicates the ID of an employee, their name, salary, and the ID of their manager.
*/

select e.name as Employee
from Employee e
join Employee m on e.managerId = m.id
where e.salary > m.salary

/*
select E1.Name
from Employee as E1, Employee as E2
where E1.ManagerId = E2.Id and E1.Salary > E2.Salary
-- 위와 같은 형태로 where 를 통한 조인으로도 가능
*/
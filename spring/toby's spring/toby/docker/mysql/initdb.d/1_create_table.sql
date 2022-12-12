CREATE DATABASE toby;
USE toby;

GRANT ALL ON `toby`.* TO 'toby'@'%';
FLUSH PRIVILEGES;

CREATE TABLE user (
    id varchar(10) primary key,
    name varchar(20) not null,
    password varchar(10) not null
);

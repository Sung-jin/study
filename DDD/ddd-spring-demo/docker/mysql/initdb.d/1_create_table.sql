create database ddd_spring_demo;
USE ddd_spring_demo;

grant all on `demo`.* to 'demo'@'%';
flush privileges;

create table purchase_order (
    order_number varchar(50) not null primary key,
    version bigint,
    orderer_id varchar(50),
    orderer_name varchar(50),
    total_amounts int,
    shipping_zip_code varchar(6),
    shipping_addr1 varchar(100),
    shipping_addr2 varchar(100),
    shipping_message varchar(200),
    receiver_name varchar(50),
    receiver_phone varchar(50),
    state varchar(20),
    order_date datetime
) character set utf8;

create table order_line (
    order_number varchar(50) not null,
    line_idx int not null,
    product_id varchar(50) not null,
    price int,
    quantity int,
    amounts int
) character set utf8;

create index order_line_idx ON order_line (order_number, line_idx);

create table category (
    category_id bigint not null primary key,
    name varchar(100)
) character set utf8;

create table product (
    product_id varchar(50) not null primary key,
    name varchar(100),
    price int,
    detail text
) character set utf8;

create table product_category (
    product_id varchar(50) not null,
    category_id bigint not null,
    constraint primary key (product_id, category_id)
) character set utf8;

create table image (
    image_id int not null auto_increment primary key,
    product_id varchar(50),
    list_idx int,
    image_type varchar(10),
    image_path varchar(255),
    upload_time datetime
) character set utf8;

create table member (
    member_id varchar(50) not null primary key,
    name varchar(50),
    password varchar(255),
    blocked boolean
) character set utf8;

create table member_authorities (
    member_id varchar(50) not null,
    authority varchar(50) not null,
    primary key (member_id, authority)
) character set utf8;

create table article (
    id int not null auto_increment primary key,
    title varchar(255)
) character set utf8;

create table article_content (
    id int not null primary key,
    content varchar(255),
    content_type varchar(255)
) character set utf8;

create table evententry (
    id int not null AUTO_INCREMENT PRIMARY KEY,
    `type` varchar(255),
    `content_type` varchar(255),
    payload MEDIUMTEXT,
    `timestamp` datetime
) character set utf8;

create table locks (
    `type` varchar(255),
    id varchar(255),
    lockid varchar(255),
    expiration_time datetime,
    primary key (`type`, id)
) character set utf8;

create unique index locks_idx ON demo.locks (lockid);

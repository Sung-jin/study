# member table 생성
create table member(
    member_id int auto_increment,
    name varchar(255) null,
    constraint member_pk primary key (member_id)
)

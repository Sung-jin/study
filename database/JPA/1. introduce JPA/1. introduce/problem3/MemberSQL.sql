# Team 테이블 생성 및 member 테이블 업데이트
create table team(
    team_id int auto_increment,
    team_name varchar(255) null,
    constraint member_pk primary key (team_id)
)

# member 에 team 컬럼 추가 및 team 에 foreign key 연결
alter table member add team int null;
alter table member add constraint member_team_team_id_fk foreign key (team) references team (team_id);



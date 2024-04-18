-- USERS
create sequence user_seq start 1 increment 1;

create table users (
                       id int8 not null,
                       archive boolean not null,
                       email varchar(255),
                       name varchar(255),
                       fio varchar(255),
                       password varchar(255),
                       role varchar(255),
                       address varchar(255),
                       telephone varchar(255),
                       activation varchar(255),
                       branch_id int8,
                       photo_id int8,
                       primary key (id)
);

-- Organization of user
create sequence branch_seq start 1 increment 1;
create table branch(
                         id int8 not null,
                         title varchar(255),
                         address varchar(255),
                         telephone varchar(255),
                         primary key (id)
);


-- Department - это абстрактный отдел в филиале клинике
-- Таких отделом может быть несколько в разных филиалах
create sequence department_seq start 1 increment 1;
create table department(
                       id int8 not null,
                       label varchar(255),
                       primary key (id)
);

-- Раскрывает детали филиала. СКОЛЬКО отделов в филиале
-- Пока без юзеров
create sequence structure_seq start 1 increment 1;
create table structure(
                           id int8 not null,
                           branch_id int8 not null,
                           department_id int8 not null,
                           primary key (id)
);

alter table if exists structure
    add constraint structure_fk_branch
        foreign key (branch_id) references branch;

alter table if exists structure
    add constraint structure_fk_department
        foreign key (department_id) references department;



-- Детали конкретного отдела
-- Туда входит деталь ветки(Отдел) - на него добавляем наших ЮЗЕРОВ
-- Потом можно будет добавлять туда врачей по фильтру специальности
create sequence team_seq start 1 increment 1;
create table team(
                              id int8 not null,
                              structure_id int8 not null,
                              user_id int8 not null,
                              primary key (id)
);

alter table if exists team
    add constraint team_fk_structure
        foreign key (structure_id) references structure;

alter table if exists team
    add constraint team_fk_user
        foreign key (user_id) references users;


-- Список всех профессий
create sequence profession_seq start 1 increment 1;
create table profession(
                           id int8 not null,
                           title varchar(255),
                           primary key (id)
);

-- Весь спектр профессий находится в ПОРТФОЛИО ЮЗЕРА!!!
-- В качестве сведений о дипломах будет значение о них в комментариях diploma
create sequence portfolio_seq start 1 increment 1;
create table portfolio(
                          id int8 not null,
                          user_id int8 not null,
                          profession_id int8 not null,
                          diploma varchar(255),
                          primary key (id)
);

alter table if exists portfolio
    add constraint portfolio_fk_user
        foreign key (user_id) references users;

alter table if exists portfolio
    add constraint portfolio_fk_profession
        foreign key (profession_id) references profession;



create sequence schedule_seq start 1 increment 1;
create table schedule(
                     id int8 not null,
                     team_id int8 not null,
                     slot timestamp not null,
                     occupy boolean not null,
                     primary key (id)
);

alter table if exists schedule
    add constraint schedule_fk_team
        foreign key (team_id) references team;


-- Создаем все записи-встречи

create sequence meeting_seq start 1 increment 1;
create table meeting(
                          id int8 not null,
                          schedule_id int8 not null,
                          user_id int8 not null,
                          doctor_id int8 not null,
                          changed timestamp,
                          updated timestamp,
                          paid_id int8,
                          status_id int8,
                          sum numeric(19, 2),
                          primary key (id)
);


alter table if exists meeting
    add constraint meeting_fk_schedule
        foreign key (schedule_id) references schedule;

alter table if exists meeting
    add constraint meeting_fk_patient
        foreign key (user_id) references users;

alter table if exists meeting
    add constraint meeting_fk_doctor
        foreign key (doctor_id) references users;








-- Organization of user
create sequence photo_seq start 1 increment 1;

create table photo(
                             id int8 not null,
                             title varchar(255),
                             primary key (id)
);


-- Сделать множество справочников для специальностей
-- а пока сделать так, чтобы был единый справочник


alter table if exists users
    add constraint users_fk_branch
        foreign key (branch_id) references branch;

alter table if exists users
    add constraint users_fk_photo
        foreign key (photo_id) references photo;

-- alter table if exists users
--     add constraint users_fk_position
--         foreign key (position_id) references profession;



-- Images
create sequence images_seq start 1 increment 1;

create table images(
                       id int8 not null,
                       name varchar(255),
                       originalFileName varchar(255),
                       size int8,
                       contentType varchar(255),
                       content bytea,
                       primary key (id)
);


-- News
create sequence news_seq start 1 increment 1;
create table news (
                      id int8 not null,
                      title varchar(255),
                      message text,
                      created timestamp,
                      changed timestamp,
                      active boolean not null,
                      user_id int8,
                      image_id int8,
                      primary key (id)
);

alter table if exists news
    add constraint news_fk_users
        foreign key (user_id) references users;

alter table if exists news
    add constraint news_fk_image
        foreign key (image_id) references images;

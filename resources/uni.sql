create table DEPARTMENTS
(
    CODE         VARCHAR(10) not null
        primary key,
    NAME         VARCHAR(30) not null,
    FACULTY_CODE VARCHAR(5)  not null,
    CHAIR_ID     INTEGER
);

create table INSTRUCTORS
(
    ID              INTEGER     not null
        primary key,
    NAME            VARCHAR(30) not null,
    SURNAME         VARCHAR(30),
    DEPARTMENT_CODE VARCHAR(10) not null
        references DEPARTMENTS
);

alter table DEPARTMENTS
    add foreign key (CHAIR_ID) references INSTRUCTORS;

create table STUDENTS
(
    ID              INTEGER     not null
        primary key,
    NAME            VARCHAR(30) not null,
    SURNAME         VARCHAR(30) not null,
    DEPARTMENT_CODE VARCHAR(10) not null
        references DEPARTMENTS,
    EMAIL           VARCHAR(50)
        unique
);


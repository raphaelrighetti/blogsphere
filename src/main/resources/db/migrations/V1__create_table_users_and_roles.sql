alter table if exists users 
		drop constraint if exists FKp56c1712k691lhsyewcssf40f;
		
drop table if exists roles cascade;
drop table if exists users cascade;

create table roles (
		id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
);

create table users (
        active boolean not null,
        id bigserial not null,
        role_id bigint not null,
        description varchar(255),
        email varchar(255) not null unique,
        password varchar(255) not null,
        picture_url varchar(255),
        public_user_name varchar(255) not null unique,
        primary key (id)
);

alter table if exists users 
       add constraint ROLE_ID 
       foreign key (role_id) 
       references roles;
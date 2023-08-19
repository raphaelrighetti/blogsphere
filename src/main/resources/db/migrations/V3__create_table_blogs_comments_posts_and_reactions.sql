alter table if exists blogs 
       drop constraint if exists BLOG_USER_ID;

alter table if exists comments 
       drop constraint if exists PARENT_COMMENT_ID;

alter table if exists comments 
       drop constraint if exists POST_ID;

alter table if exists comments 
       drop constraint if exists COMMENT_USER_ID;

alter table if exists posts 
       drop constraint if exists POST_BLOG_ID;

alter table if exists reactions 
       drop constraint if exists REACTION_COMMENT;

alter table if exists reactions 
       drop constraint if exists REACTION_POST;

alter table if exists reactions 
       drop constraint if exists REACTION_USER;

drop table if exists blogs cascade;

drop table if exists comments cascade;

drop table if exists posts cascade;

drop table if exists reactions cascade;

create table blogs (
        id bigserial not null,
        user_id bigint,
        description varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
);

create table comments (
        id bigserial not null,
        parent_comment_id bigint,
        post_id bigint,
        user_id bigint,
        content varchar(255) not null,
        primary key (id)
);

create table posts (
        blog_id bigint,
        id bigserial not null,
        content varchar(255) not null,
        description varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
);

create table reactions (
        comment_id bigint,
        id bigserial not null,
        post_id bigint,
        user_id bigint,
        type varchar(255) not null check (type in ('LIKE','LOVE','FUNNY','SAD','ANGRY')),
        primary key (id)
);

alter table if exists blogs 
       add constraint BLOG_USER_ID 
       foreign key (user_id) 
       references users;

alter table if exists comments 
       add constraint PARENT_COMMENT_ID 
       foreign key (parent_comment_id) 
       references comments;

alter table if exists comments 
       add constraint POST_ID 
       foreign key (post_id) 
       references posts;

alter table if exists comments 
       add constraint COMMENT_USER_ID 
       foreign key (user_id) 
       references users;

alter table if exists posts 
       add constraint POST_BLOG_ID 
       foreign key (blog_id) 
       references blogs;

alter table if exists reactions 
       add constraint REACTION_COMMENT 
       foreign key (comment_id) 
       references comments;

alter table if exists reactions 
       add constraint REACTION_POST 
       foreign key (post_id) 
       references posts;

alter table if exists reactions 
       add constraint REACTION_USER 
       foreign key (user_id) 
       references users;

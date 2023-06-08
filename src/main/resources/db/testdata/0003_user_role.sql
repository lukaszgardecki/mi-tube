--liquibase formatted sql
--changeset lukasz:3
insert into
    user_role (name, description)
values
    ('ADMIN', 'The highest level of access that allows to manage account settings'),
    ('USER', 'The lowest level of access that allows to rate the movies'),
    ('EDITOR', 'A basic level of access that allows to rate the movies and manage the content');

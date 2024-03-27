CREATE TABLE todo_users
(
    id       SERIAL PRIMARY KEY,
    email    varchar unique not null,
    name     varchar        not null,
    password varchar        not null,
    user_zone varchar       default 'UTC'
);
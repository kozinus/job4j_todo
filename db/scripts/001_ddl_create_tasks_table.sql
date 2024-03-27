CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   title TEXT not null,
   description TEXT not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   done BOOLEAN not null
);
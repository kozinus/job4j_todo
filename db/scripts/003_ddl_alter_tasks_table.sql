alter table tasks add user_id
int not null references todo_users(id);
insert into roles (name)
values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users(name, surname, username, password)
values ('Sasha', 'Strukov', 'user', '$2a$12$34iUUFiGUV4IhRxCOwGJ2OKjaWSNCh.x6I2w4CCBc5syf38jGV8nq');

insert into users_roles (user_id, role_id)
values (1, 2);
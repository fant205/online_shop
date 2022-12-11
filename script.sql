/*
ДЗ № 1
*/

--Таблицы для хранения пользователя и пароля
CREATE TABLE sec_users (
	id serial primary key,
	login varchar(50) NOT NULL,
	password varchar(100) NOT NULL	
);

INSERT INTO sec_users (login, password)
VALUES
('admin', 'admin'),
('manager', 'manager'),
('superadmin', 'superadmin');

--Таблица для хранения ролей для пользователей
CREATE TABLE sec_roles (	
	id serial primary key,
	name text not null	
);

INSERT INTO sec_roles (name)
VALUES
('ROLE_ADMIN'),
('ROLE_MANAGER'),
('ROLE_SUPERADMIN');

--таблица для хранения связей между пользователями и их ролями
create table sec_users_roles (
	user_id integer not null,
	role_id integer not null,
	foreign key (user_id) references sec_users(id),
	foreign key (role_id) references sec_roles(id),
	unique (user_id, role_id)
);

insert into sec_users_roles
values
(1, 1),
(2, 2),
(3, 3);


--Категории товаров
create table categories(
	id serial primary key,
	name text not null
);

--Таблица для хранения товаров
create table products(
	id serial primary key,
	name text not null,
	cost integer not null,
	category_id integer not null,
	count integer not null -- количество товара
);


--Таблица хранения детальной информации по пользователю сделавшему заказ
create table users(
	id serial primary key,
	first_name text not null,
	last_name text not null,	
	card_number text,
	user_id integer,
	foreign key (user_id) references sec_users(id)
);

--Страны
create table countries(
	id serial primary key,
	name text not null
);

--Города
create table cities(
	id serial primary key,
	name text not null
);

--Улицы
create table streets(
	id serial primary key,
	name text not null
);

--Адрес
create table address(
	id serial primary key,
	country_id integer not null,
	city_id integer not null,
	street_id integer not null,
	building text not null,
	appartment text not null,
	foreign key (country_id) references countries (id),
	foreign key (city_id) references cities(id),
	foreign key (street_id) references streets(id)	
);


--Заказ
create table orders(
	id serial primary key,
	user_details_id integer not null,
	created date not null,
	updated date not null,
	isPayed boolean not null,
	address_id integer not null,
	delivery_status_id integer not null,
	foreign key (user_details_id) references users(id),	
	foreign key (address_id) references sec_users(id),
	foreign key (delivery_status_id) references categories(id)
);




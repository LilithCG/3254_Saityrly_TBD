CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	login VARCHAR(128) UNIQUE,
	email VARCHAR(128) UNIQUE,
    password VARCHAR(128),
    name VARCHAR(128),
    manager BOOLEAN
);

CREATE TABLE adresses(
    user_id INT NOT NULL REFERENCES users(id),
	adress VARCHAR(255),
	PRIMARY KEY (user_id,adress)
);

CREATE TABLE orders(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
	adress VARCHAR(255) NOT NULL,
	FOREIGN KEY (user_id, adress) REFERENCES adresses (user_id, adress),
	amount DECIMAL,
	status VARCHAR(255),
	order_date DATE
);

CREATE TABLE menu(
    id SERIAL PRIMARY KEY,
	name VARCHAR(255),
	price DECIMAL,
	weight DECIMAL,
	composition VARCHAR(255),
	protein DECIMAL,
	fats DECIMAL,
	carbs DECIMAL,
	kcal DECIMAL
);

CREATE TABLE basket(
    id SERIAL PRIMARY KEY,
	user_id INT NOT NULL REFERENCES users(id),
	menu_id INT NOT NULL REFERENCES menu(id),
	order_id INT NOT NULL REFERENCES orders(id),
	count INT NOT NULL
);

--1)Пользователь. Регистрация
INSERT INTO users (login, email, password, name, manager)
VALUES ('login', 'email', 'password', 'name', false);

--2)Пользователь. Авторизация
SELECT * 
FROM users
WHERE login = 'login' AND  password = 'password';

--3)Пользователь. Просмотр меню.
SELECT * 
FROM menu

--4)Пользователь. Добавление в корзину
INSERT INTO basket (user_id, menu_id,order_id,count)
VALUES (1,1,1,1);

--5)Пользователь. Оформление заказа (Считаем, что оплата производится «автоматом»)
INSERT INTO adresses (user_id, adress)
VALUES(1,'ул.Пушкина дом18 квартира 8');

INSERT INTO orders (user_id, adress,amount,status,order_date)
VALUES (1,'ул.Пушкина дом18 квартира 8',NULL,'Готовится',now());

UPDATE orders SET amount = (SELECT SUM(menu.price*basket.count) 
FROM menu, basket, orders
WHERE menu.id = basket.menu_id AND orders.id = basket.order_id AND order_id = 1)
WHERE order_id = 1

--6)Пользователь. Просмотр оформленных ранее заказов с пагинацией и поиском по диапазону дат и блюд из меню, а также с сортировкой по ценнику и дате
SELECT *
FROM orders
WHERE user_id = 1
ORDER BY order_date

SELECT *
FROM orders
WHERE user_id = 1
ORDER BY order_date DESC

SELECT *
FROM orders
WHERE user_id = 1
ORDER BY amount

SELECT *
FROM orders
WHERE user_id = 1
ORDER BY amount DESC

SELECT *
FROM orders
WHERE user_id = 1 AND order_date BETWEEN '2019-09-1' AND '2023-02-01'
ORDER BY order_date

SELECT *
FROM orders
WHERE user_id = 1 AND order_date BETWEEN '2019-09-1' AND '2023-02-01'
ORDER BY order_date DESC

SELECT orders.*
FROM orders, basket
WHERE basket.user_id = 1 AND basket.menu_id = 1S
ORDER BY AMOUNT

SELECT orders.*
FROM orders, basket
WHERE basket.user_id = 1 AND basket.menu_id = 1
ORDER BY AMOUNT DESC

--7)Пользователь. Возможность повторить (добавить в корзину) все блюда из ранее оформленного заказа

INSERT INTO basket (user_id, menu_id,order_id,count)
VALUES (1,1,2,2);


--8)Пользователь. Просмотр состояния заказа
SELECT * 
FROM orders
WHERE id=1;

--9)Управляющий. Добавление новых позиций в меню (должен быть указан состав, вес, белки, жиры, углеводы, ккал)
INSERT INTO menu (name, price,weight,composition,protein,fats,carbs,kcal)
VALUES ('Пепперони',350,400,'Колбаса,тесто',6,2,1,220);

--10)Управляющий. Видеть список заказов
SELECT *
FROM orders

--11)Управляющий. Менять статус заказа
UPDATE orders SET status = 'Отмена'
WHERE id = 1;
CREATE TABLE student(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	surname VARCHAR(255),
	address VARCHAR(255),
	n_group INT,
	CHECK (n_group >= 1000 AND n_group <= 9999),
	score REAL, 
	CHECK (score >= 2 AND score <= 5)
);

CREATE TABLE hobby(
	id SERIAL PRIMARY KEY,
	name varchar(255) NOT NULL,
	risk NUMERIC(3, 2) NOT NULL
);

CREATE TABLE student_hobby(
	id SERIAL PRIMARY KEY,
	student_id INT NOT NULL REFERENCES student(id),
	hobby_id INT NOT NULL REFERENCES hobby(id),
	date_start TIMESTAMP NOT NULL,
	date_end DATE
);

INSERT INTO student (name, surname, address, n_group, score) VALUES ('София', 'Орлова', 'Дубна', 4254, 4.9);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Александр', 'Зеленин', 'Ижевск', 4254, 3.5);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Алеся', 'Сергеева', 'Дубна', 4254, 3.8);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Ева', 'Ковалева', 'Москва', 3253, 3.9);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Владимир', 'Маслов', 'Ростов', 1251, 2.1);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Константин', 'Степанов', 'Ростов', 3253, 4.5);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Виктория', 'Волкова', 'Ижевск', 4254, 4.2);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Евгений', 'Максимов', 'Казань', 4254, 4.9);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Яна', 'Ермолаева', 'Казань', 3253, 4.2);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Андрей', 'Серый', 'Дубна', 1251, 4.3);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Макар', 'Демидов', 'Москва', 2252, 3.3);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Илья', 'Токарев', 'Ижевск', 1251, 3.8);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Егор', 'Морозов', 'Ростов', 4254, 3.4);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Анастасия', 'Власова', 'Казань', 3253, 4.0);
INSERT INTO student (name, surname, address, n_group, score) VALUES ('Никита', 'Гончаров', 'Дубна', 2252, 5.0);

INSERT INTO hobby (name, risk) VALUES ('шахматы', 0);
INSERT INTO hobby (name, risk) VALUES ('футбол', 5);
INSERT INTO hobby (name, risk) VALUES ('баскетбол', 3);
INSERT INTO hobby (name, risk) VALUES ('плавание', 4);
INSERT INTO hobby (name, risk) VALUES ('киберспорт', 0);

INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (1, 1, '11-12-2017 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (1, 2, '01-01-2019 12:12:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (2, 3, '12-12-2017 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (3, 2, '08-01-2012 12:15:59', '04-12-2020');
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (3, 3, '07-12-2020 11:01:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (13, 4, '12-12-2011 10:14:59', '02-01-2012');
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (4, 4, '08-08-2015 09:14:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (5, 5, '02-12-2017 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (1, 1, '05-06-2018 12:14:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (11, 2, '02-24-2018 14:14:59', '04-06-2018');
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (9, 3, '12-12-2017 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (8, 2, '03-11-2020 13:13:59', null);
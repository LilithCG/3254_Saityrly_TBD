CREATE TABLE student(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	surname VARCHAR(255),
	address VARCHAR(255),
	n_group INT,
	CHECK (n_group >= 1000 AND n_group <= 9999),
	score REAL, 
	CHECK (score >= 2 AND score <= 5),
	date_birth DATE
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

INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('София', 'Орлова', 'Дубна', 4254, 4.9, '1999-09-09');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Александр', 'Зеленин', 'Ижевск', 4254, 3.5, '1997-09-09');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Алеся', 'Сергеева', 'Дубна', 4254, 3.8, '2000-02-21');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Ева', 'Ковалева', 'Москва', 3253,  3.9, '1999-03-29');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Владимир', 'Маслов', 'Ростов', 1251, 2.1, '1996-11-19');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Константин', 'Степанов', 'Ростов', 3253,  4.5, '2000-06-25');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Виктория', 'Волкова', 'Ижевск', 4254,  4.2, '1999-03-22');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Евгений', 'Максимов', 'Казань', 4254,  4.9, '2000-11-16');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Яна', 'Ермолаева', 'Казань', 3253, 4.2, '2000-09-12');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Андрей', 'Серый', 'Дубна', 1251, 4.3, '1999-05-21');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Макар', 'Демидов', 'Москва', 2252, 3.3, '2000-08-22');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Илья', 'Токарев', 'Ижевск', 1251, 3.8, '2001-07-23');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Егор', 'Морозов', 'Ростов', 4254, 3.4, '2000-04-13');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Анастасия', 'Власова', 'Казань', 3253, 4.0, '2002-02-12');
INSERT INTO student (name, surname, address, n_group, score, date_birth) VALUES ('Никита', 'Гончаров', 'Дубна', 2252, 5.0, '1999-01-11');

INSERT INTO hobby (name, risk) VALUES ('шахматы', 0);
INSERT INTO hobby (name, risk) VALUES ('футбол', 5);
INSERT INTO hobby (name, risk) VALUES ('баскетбол', 3);
INSERT INTO hobby (name, risk) VALUES ('плавание', 4);
INSERT INTO hobby (name, risk) VALUES ('киберспорт', 0);

INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (1, 1, '2017-11-12 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (1, 2, '2019-01-01 12:12:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (2, 3, '2017-12-12 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (3, 2, '2012-08-01 12:15:59', '2020-04-12');
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (3, 3, '2020-07-12 11:01:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (13, 4, '2011-12-12 10:14:59', '2012-02-01');
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (4, 4, '2015-08-08 09:14:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (5, 5, '2017-02-12 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (1, 1, '2018-05-06 12:14:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (11, 2, '2018-09-20 14:14:59', '2018-04-06');
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (9, 3, '2017-12-12 14:32:59', null);
INSERT INTO student_hobby (student_id, hobby_id, date_start, date_end) VALUES (8, 2, '2020-03-11 13:13:59', null);
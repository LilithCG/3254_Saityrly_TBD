--1 
SELECT st.name, st.surname, hb.name
FROM student st
JOIN student_hobby sh ON st.id = sh.student_id
JOIN hobby hb ON hb.id = sh.hobby_id;

--2
SELECT *
FROM student st
INNER JOIN student_hobby sh on sh.student_id = st.id and sh.date_end IS NULL
ORDER BY sh.date_start
LIMIT 1;

--3
SELECT st.name, st.surname, st.id, st.date_birth
FROM student st	
JOIN student_hobby sh ON sh.student_id = st.id 
JOIN hobby hb ON hb.id = sh.hobby_id
WHERE score > (SELECT AVG(score) FROM student) 
GROUP BY st.id 
HAVING SUM(hb.risk) > 0.9;

--4
SELECT st.name, st.surname, st.id, st.date_birth, hb.name, 12*extract(year from age(now()::date, sh.date_start))+extract(month from age(date_end, date_start)) as hobby_month
FROM student st
JOIN student_hobby sh ON sh.student_id = st.id
JOIN hobby hb ON hb.id = sh.hobby_id
WHERE (sh.date_end - sh.date_start) IS NOT NULL;

--5
SELECT st.name, st.surname, st.id, st.date_birth
FROM student st
JOIN student_hobby sh ON sh.student_id = st.id
JOIN hobby hb ON hb.id = sh.hobby_id
GROUP BY st.id HAVING SUM(hb.id) > 1;

--6
SELECT st.n_group, AVG(st.score)
FROM student st
INNER JOIN student_hobby sh ON sh.student_id = st.id AND sh.date_end IS NULL
GROUP BY n_group;

--7
SELECT st.id, st.name, hobby.name, hobby.risk, 12*extract(year from age(now()::date, sh.date_start))+extract(month from age(now()::date, sh.date_start)) as hobby_month
FROM student_hobby sh
INNER JOIN student st on sh.student_id = st.id
INNER JOIN hobby on sh.hobby_id = hobby.id
WHERE date_end IS NULL
ORDER BY hobby_month DESC
LIMIT 1;

--8
SELECT hb.name, MAX(st.score)
FROM student_hobby sh
JOIN hobby hb ON hb.id = sh.hobby_id
JOIN student st ON st.id = sh.student_id
GROUP BY hb.name
ORDER BY MAX(st.score) DESC LIMIT 1;

--9
SELECT hb.name, st.score
FROM hobby hb
JOIN student_hobby sh ON sh.hobby_id = hb.id
JOIN student st ON st.id = sh.student_id
WHERE st.score>=2.5 and st.score<3.5 AND st.n_group / 1000 = 2;

--10
SELECT st.n_group / 1000 course
FROM student st
INNER JOIN (SELECT st.id, st.n_group / 1000 course, COUNT(*) count_check
FROM student st
JOIN student_hobby sh ON sh.student_id = st.id
GROUP BY st.id, st.n_group
HAVING COUNT(*) > 1
) a ON st.n_group / 1000 = a.course
GROUP BY st.n_group / 1000, a.count_check
HAVING a.count_check / COUNT(*) > 0.5;

--11
SELECT st.n_group, COUNT(*), a.good_stud
FROM student st
INNER JOIN
(SELECT n_group, COUNT(*) good_stud
FROM student
WHERE SCORE >= 4
GROUP BY n_group
) a ON a.n_group = st.n_group
GROUP BY st.n_group, a.good_stud
HAVING a.good_stud::real/COUNT(*)::real >= 0.6;

--12
SELECT st.n_group / 1000 as course, COUNT(DISTINCT sh.hobby_id)
FROM student st
LEFT JOIN student_hobby sh ON sh.student_id = st.id
GROUP BY st.n_group;

--13
SELECT st.id, st.surname, st.name, st.date_birth, st.n_group / 1000 as course
FROM student st
LEFT JOIN student_hobby sh ON sh.student_id = st.id
WHERE sh.id IS NULL AND st.score > 4.5
ORDER BY st.n_group / 1000, st.date_birth DESC;

--14
CREATE OR REPLACE VIEW view0 AS
SELECT st.id, st.name, st.surname, st.n_group, NOW() - sh.date_start AS duration
FROM student st
LEFT JOIN student_hobby AS sh ON sh.student_id = st.id
WHERE sh.id IS NOT NULL
AND sh.date_end IS NULL
AND extract(year from age(now(), sh.date_start)) >= 5;

--15
SELECT hb.name, COUNT(sh.student_id)
FROM hobby hb
LEFT JOIN student_hobby sh ON sh.hobby_id = hb.id
GROUP BY hb.name;

--16
SELECT hb.id 
FROM hobby hb
LEFT JOIN student_hobby sh ON sh.hobby_id = hb.id
GROUP BY hb.id
ORDER BY COUNT(sh.student_id) DESC LIMIT 1;

--17
SELECT *
FROM student st
LEFT JOIN student_hobby sh ON st.id = sh.student_id
WHERE sh.hobby_id IN
(SELECT hb.id 
FROM hobby hb
LEFT JOIN student_hobby sh ON sh.hobby_id = hb.id
GROUP BY hb.id
ORDER BY COUNT(sh.student_id) DESC LIMIT 1);

--18
SELECT id
FROM hobby hb
ORDER BY risk DESC LIMIT 3;

--19
SELECT st.*
FROM student_hobby sh
INNER JOIN student st ON sh.student_id = st.id
WHERE sh.date_end IS NULL 
GROUP BY st.id, sh.date_start
ORDER BY (now() - sh.date_start) DESC
LIMIT 10;

--20
SELECT DISTINCT n_group
FROM student
WHERE n_group IN
(
SELECT n_group
FROM student_hobby sh
INNER JOIN student st ON sh.student_id = st.id
WHERE sh.date_end IS NULL 
GROUP BY st.id, sh.date_start
ORDER BY (now() - sh.date_start) DESC
LIMIT 10
)

--21
CREATE OR REPLACE VIEW view1 AS
SELECT id, surname, name FROM student
ORDER BY score DESC;

--22
SELECT ina.course, ina.hbname
FROM(
SELECT n_group/1000 course, hb.name hbname, COUNT(hb.name) counthb,
ROW_NUMBER() OVER (PARTITION BY n_group/1000 ORDER BY COUNT(hb.name) DESC) row_c
FROM student st
RIGHT JOIN student_hobby sh ON sh.student_id = st.id
LEFT JOIN hobby hb ON hb.id = sh.hobby_id
GROUP BY n_group/1000, hb.name) ina
WHERE row_c<= 1
GROUP BY ina.course, ina.hbname;

--23
CREATE OR REPLACE VIEW view2 AS
SELECT hb.NAME, COUNT(*) popul, hb.risk
FROM student st
INNER JOIN student_hobby sh ON sh.student_id = st.id
INNER JOIN hobby hb ON hb.id = sh.hobby_id
WHERE st.n_group / 1000 = 2
GROUP BY hb.NAME, hb.risk
ORDER BY COUNT(*) DESC, hb.risk DESC
LIMIT 1;

--24
CREATE OR REPLACE VIEW view3 AS
SELECT st.n_group / 1000 Course, COUNT(*) Course_stud, 
CASE
    WHEN a.verygoodstud IS NULL THEN 0
    ELSE a.verygoodstud
END verygoodstud
FROM student st
LEFT JOIN (SELECT st.n_group, COUNT(*) verygoodstud
    FROM student st
    WHERE st.score > 4.5
    GROUP BY st.n_group) a ON a.n_group = st.n_group

GROUP BY st.n_group / 1000, a.verygoodstud;

--25
CREATE OR REPLACE VIEW view4 AS
SELECT hb.name FROM student st
LEFT JOIN student_hobby sh ON st.id = sh.student_id
LEFT JOIN hobby hb ON hb.id = sh.hobby_id
WHERE hb.name IS NOT NULL
GROUP BY hb.name
ORDER BY COUNT(*) DESC 
LIMIT 1;

--26
CREATE OR REPLACE VIEW upd_view AS
SELECT * FROM student
WITH CHECK OPTION;

--27
SELECT LEFT(name, 1), MAX(score), MIN(score), AVG(score) 
FROM student
GROUP BY LEFT(name, 1)
HAVING MAX(score) > 3.6

--28
SELECT n_group/1000 course, surname,  MAX(score), MIN(score)
FROM student
GROUP BY n_group / 1000, surname

--29
SELECT extract(year from date_birth), COUNT(*)
FROM student st
LEFT JOIN student_hobby sh ON sh.student_id = st.id
WHERE sh.hobby_id IS NOT NULL
GROUP BY extract(year from date_birth);

--30
SELECT regexp_split_to_table(st.name,''), MIN(hb.risk), MAX(hb.risk)
FROM student st
INNER JOIN student_hobby sh ON sh.student_id = st.id
INNER JOIN hobby hb ON hb.id = sh.hobby_id
GROUP BY regexp_split_to_table(st.name,'');

--31
SELECT extract(month from st.date_birth), avg(st.score)
FROM student st
INNER JOIN student_hobby sh ON sh.student_id = st.id
INNER JOIN hobby hb ON sh.hobby_id = hb.id
WHERE hb.name LIKE 'футбол'
GROUP BY extract(month from st.date_birth);

--32
SELECT st.name имя:, st.surname фамилия:, st.n_group группа:
FROM student st
RIGHT JOIN student_hobby sh ON sh.student_id = st.id
LEFT JOIN hobby hb ON sh.hobby_id = hb.id
GROUP BY st.id;

--33
SELECT 
CASE
	WHEN strpos(st.surname, 'ов') != 0 THEN strpos(st.surname, 'ов')::varchar(255)
	ELSE 'Not found'
END
FROM student st;

--34
SELECT RPAD(st.surname, 10, '#')
FROM student st;

--35
SELECT REPLACE(st.surname, '#', '')
FROM student st;

--36
SELECT DATE_PART('days', date_trunc('month', '2018-04-01'::date) + '1 month'::interval - '1 day'::interval);

--37
SELECT date_trunc('week', now())::date + 5;

--38
SELECT 
extract(century from now()) century,
extract(week from now()) week,
extract(doy from now()) day_of_the_year;

--39
SELECT st.name, st.surname, hb.name,
CASE
	WHEN sh.date_end IS NULL THEN 'is engaged'
	ELSE 'finished'
END status
FROM student st
INNER JOIN student_hobby sh ON sh.student_id = st.id
INNER JOIN hobby hb ON hb.id = sh.hobby_id;

--40

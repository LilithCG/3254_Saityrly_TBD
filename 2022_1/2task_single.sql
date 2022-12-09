-- 1
-- способ 1
SELECT name, surname FROM student WHERE score BETWEEN 4 AND 4.5;
-- способ 2
SELECT name, surname FROM student WHERE score >= 4 AND score <= 4.5;

-- 2
SELECT * 
FROM student 
WHERE n_group::varchar LIKE '4%';

-- 3
SELECT * 
FROM student 
ORDER BY n_group DESC, name;

--4
SELECT * 
FROM student 
WHERE score > 4 
ORDER BY score DESC;

--5
SELECT name, risk 
FROM hobby 
WHERE name LIKE 'шахматы' OR name LIKE 'футбол';

--6
SELECT hobby_id, student_id 
FROM student_hobby 
WHERE (date_start BETWEEN '2012-01-01' AND '2019-01-01') AND date_end IS NOT NULL;

--7
SELECT * 
FROM student 
WHERE score >= 4.5 
ORDER BY score DESC;

--8
-- способ 1
SELECT * 
FROM student W
WHERE score >= 4.5 
ORDER BY score DESC 
LIMIT 5;

-- способ 2
SELECT *
FROM student
WHERE score > 4.5
ORDER BY score DESC
FETCH FIRST 5 ROWS ONLY;

--9
SELECT name,
CASE
	WHEN risk >= 8 THEN 'очень высокий'
	WHEN risk >= 6 AND risk < 8 THEN 'высокий'
	WHEN risk >= 4 AND risk < 6 THEN 'средний'
	WHEN risk >= 2 AND risk < 4 THEN 'низкий'
	WHEN risk < 2 THEN 'очень низкий'
END
FROM hobby;

--10
SELECT *
FROM hobby
ORDER BY risk DESC
LIMIT 3;
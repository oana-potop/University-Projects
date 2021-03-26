use sem2MP
go

--UNION
--union of songs that last longer than 4 minutes and the albums with less than 10 tracks on them
select title from Song
where length > 4
union
select title from Album
where number_of_tracks<10

--INTERSECT
--the songs on the albums released after 2000 and which have more than 9 tracks on them
select S.title 
from Song S, Album A
where S.alid = A.alid and A.release_year>2000
intersect
select S2.title
from Song S2, Album A2
where S2.alid = A2.alid and A2.number_of_tracks > 9

--EXCEPT
--those songs on the albums released after 2000, except for those released after 2000 (between 2000 and 2002)
select S.title 
from Song S, Album A
where S.alid = A.alid and A.release_year>=2000
except
select S2.title
from Song S2, Album A2
where S2.alid = A2.alid and A2.release_year>=2002

--RIGHT JOIN 
--the titles of the songs from each album
select A.name from Artist A
right join Album Ab
on A.aid = Ab.alid

--LEFT JOIN
--the titles of the songs from each album
select S.title from Song S
left join Album A
on S.alid = A.alid

--INNER JOIN
--the titles of the albums of each artist
select A.name from Artist A
inner join Album B
on A.name = B.title

--FULL JOIN
--the names of the artists of each song
select A.name from Artist A
full join Song S
on A.aid = S.sid

--INNER JOIN WITH 3 TABLES
--the songs on the albums that have the same name as the artist
select A.name from Artist A
inner join  Album B on A.name = B.title
inner join Song S on A.aid = S.sid

--GROUP BY
--counting the artists with the same name
select count (aid), name
from Artist
group by name

--grouping the albums by the number of tracks they have and selecting those where a number of tracks appears more than once
select count (alid), number_of_tracks
from Album
group by number_of_tracks
having count(alid) > 1

--grouping the albums by the number of tracks they have and selecting those where a number of tracks appears more than the number of albums with id less than 1
select count (alid), number_of_tracks
from Album
group by number_of_tracks
having count(alid) > (select alid from Album where alid<=1)

--EXISTS
--the titles of the albums which have songs on them and are released after 2000
select A.title from Album A
where exists (select S.title from Song S where S.alid=A.alid and A.release_year>2000)

--IN
--the artists who have an album with the same name as theirs
select * from Artist A
where name in (select B.title from Album B where A.name=B.title)

--FROM SUBQUERY
--the title and the length whose id is equal to the average id of the albums
select S.title, S.length
from (select avg(alid) as avgalid from Album ) as aa, Song as S
where S.alid=aa.avgalid

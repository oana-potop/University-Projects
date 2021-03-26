use sem2MP
go

-- checks if a variable is int
create or alter function CheckIfInt(@x int)
returns bit as
begin
	declare @ok bit
	set @ok = isnumeric(@x)
	return @ok
end
go

-- checks if a variable is a varchar
create or alter function CheckIfNVarchar (@x nvarchar(50))
returns bit as
begin
	declare @ok bit
	if (@x like '[a-z]%' or @x like '[A-Z]%')
		set @ok=1
	else
		set @ok=0
	return @ok
end
go

create or alter procedure insertIntoArtist (@id int, @name nvarchar(50), @debut int)
as
	if dbo.CheckIfInt(@id) = 1 and dbo.CheckIfNVarchar(@name) = 1 and dbo.CheckIfInt(@debut) = 1
	begin
		insert into Artist(aid, name, debut) values(@id, @name, @debut)
		print 'Values added successfully'
	end
	else
		print 'Incorrect input data'
go
select * from Artist
select * from performs
select * from Song
select * from Album 
exec insertIntoArtist @id = 6, @name = N'lalala', @debut = 1999
exec insertIntoArtist @id = 9, @name = N'lalalali', @debut = 1999


--2. create a view that operates on at least 4 tables; write a SELECT on the view that returns useful information for a potential user;
create or alter view InJoin4Tables
as
select A.name 
from Artist A
inner join performs P on P.aid = A.aid
inner join Song S on S.sid = P.sid
inner join  Album Al on Al.alid = S.alid
go
select * from InJoin4Tables

--3. implement a trigger for a table, for INSERT, UPDATE, and DELETE; the trigger will introduce the following data in a log table: 
--the date and time of the triggering statement, its type (INSERT / UPDATE / DELETE), the name of the affected table, and the number of added / modified / removed records;
create table OperationsLog(
triggerDate datetime,
triggerType varchar(50),
nameChangedTable varchar(50),
noRows int)

drop table OperationsLog

create or alter trigger trg_insertIntoArtist
on Artist
for insert
as
begin
	insert into OperationsLog(triggerDate, triggerType, nameChangedTable, noRows) values(CURRENT_TIMESTAMP, 'insert', 'Artist', @@ROWCOUNT)
end
go

create or alter trigger trg_deleteFromArtist
on Artist
for delete
as
begin
	insert into OperationsLog(triggerDate, triggerType, nameChangedTable, noRows) values(CURRENT_TIMESTAMP, 'delete', 'Artist', @@ROWCOUNT)
end
go

create or alter trigger trg_updateInArtist
on Artist
for update
as
begin
	insert into OperationsLog(triggerDate, triggerType, nameChangedTable, noRows) values(CURRENT_TIMESTAMP, 'update', 'Artist', @@ROWCOUNT)
end
go

select * from OperationsLog

insert into Artist(aid, name, debut) values (7,N'lilili', 2019), (8, N'lululu', 2018)

delete  from Artist
where  aid >=7

update Artist
set name = N'Test'
where aid = 7


-- write a query that contains at least 2 of the following operators in the execution plan:
-- clustered index scan;
-- clustered index seek;
-- nonclustered index scan;
-- nonclustered index seek;
-- key lookup.

if exists (select name from sys.indexes where name=N'nonclusteredIndex1')
	drop index nonclusteredIndex1 on dbo.Artist;
go

create nonclustered index nonclusteredIndex1 on dbo.Artist(name);
go

select * from Artist
order by name
go

select * from Artist
go

if exists (select name from sys.indexes where name=N'nonclusteredIndex2')
	drop index nonclusteredIndex2 on dbo.Artist;
go

create nonclustered index nonclusteredIndex2 on dbo.Artist(name);
go

select A.name from Artist A, performs P, Song S
where A.aid=P.aid
and P.sid = S.sid
and S.sid > 3
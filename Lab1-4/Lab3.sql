use sem2MP
go

--procedure that adds a column to a table
create or alter procedure addColumnInPerforms
as
	alter table performs
	add place varchar(50)
	print 'Column <<place>> was added in table <<performs>>'
go

exec addColumnInPerforms

--procedure that reverts the add column procedure (removes a column)
create or alter procedure addColumnInPerforms_revert
as
	alter table performs
	drop column place
	print 'Column <<place>> was deleted from table <<performs>>'
go

exec addColumnInPerforms_revert

--procedure that deletes a primary key
create or alter procedure deletePKInPerforms
as
	alter table performs
	drop constraint PK__performs__A38D73FD42177E26
	print 'Primary key from table <<performs>> was deleted'
go

exec deletePKInPerforms

--procedure that reverts the delete-pk procedure (adds a primary key)
create or alter procedure deletePKInPerforms_revert
as
	alter table performs
	add constraint PK__performs__A38D73FD42177E26 primary key (aid, sid)
	print 'Primary key was added in table <<performs>>'
go

exec deletePKInPerforms_revert

--procedure that deletes a foreign key
create or alter procedure deleteFKInSong
as
	alter table Song
	drop constraint FK__Song__alid__3A81B327
	print 'Foreign key from table <<Song>> referencing table <<Album>> was deleted'
go

exec deleteFKInSong

--procedure that reverts the delete-fk procedure (adds a foreign key)
create or alter procedure deleteFKInSong_revert
as
	alter table Song
	add constraint FK__Song__alid__3A81B327
	foreign key (alid) references Album(alid)
	print 'Foreign key referencing table <<Album>> was added in <<Song>>'
go

exec deleteFKInSong_revert

--procedure that creates a table
create or alter procedure createTableTest
as
	create table test(
	tid int primary key,
	test_column varchar(50))
	print 'Table <<createTableTest>> was created'
go

exec createTableTest

--procedure that reverts the create-table procedure (removes a table)
create or alter procedure createTableTest_revert
as
	drop table test
	print 'Table <<createTableTest>> was deleted'
go

exec createTableTest_revert


--table that retains the current version
create table versionTable(
nr_version int)

insert into versionTable(nr_version) values(0)

select * from versionTable

--table that retains the procedure and the revert procedure corresponding to each version
create table changeVersionTable(
nr_ver int,
proce varchar(50),
proce_revert varchar(50))

insert into changeVersionTable (nr_ver, proce, proce_revert) values(1,'addColumnInPerforms','addColumnInPerforms_revert'),
(2,'deletePKInPerforms','deletePKInPerforms_revert'), 
(3, 'deleteFKInSong','deleteFKInSong_revert'),
(4, 'createTableTest','createTableTest_revert')

select * from changeVersionTable

--procedure that sets the version number
create procedure setVersion @newVersion int
as
	declare @current int = (select * from versionTable)
	update versionTable	
	set nr_version = @newVersion
	where nr_version = @current
go

exec setVersion 0

--procedure that modifies the DB as to bring it to the desired version
create or alter procedure bringDBToVersion @version int
as
	if ((@version >=0) and (@version <=4))
	begin
	declare @currentVer int = (select * from versionTable)
	if @version < @currentVer
		begin
			while @version < @currentVer
				begin
					declare @proc_rev varchar(50) = (select proce_revert from changeVersionTable where nr_ver = @currentVer )
					exec @proc_rev
					set @currentVer = @currentVer - 1
				end
		end
	if @version > @currentVer
		begin
			while @version > @currentVer
				begin
					set @currentVer = @currentVer + 1
					declare @proce varchar(50) = (select proce from changeVersionTable where nr_ver = @currentVer )
					exec @proce
				end
		end
	exec setVersion @version
	end
	else
	begin
	print 'Please give a valid version number (between 0 and 4)'
	end
go

exec bringDBToVersion 4
exec bringDBToVersion 0
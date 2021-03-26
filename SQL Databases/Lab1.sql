create database sem2MP
go
use sem2MP
go

create table Artist(
aid int primary key identity(1,1),
name nvarchar(50),
debut int)

create table Album(
alid int primary key identity(1,1),
title nvarchar(50),
release_year int,
number_of_tracks int)

create table Song(
sid int primary key identity (1,1),
title nvarchar(50),
length real,
alid int,
foreign key (alid) references Album(alid))

create table performs(
aid int,
sid int,
primary key(aid,sid),
foreign key (aid) references Artist(aid),
foreign key (sid) references Song(sid))

create table Genre(
gid int primary key identity(1,1),
name varchar(50))

create table is_of(
sid int,
gid int,
primary key(sid, gid),
foreign key (gid) references Genre(gid),
foreign key (sid) references Song(sid))

create table Records_Label(
rid int primary key identity(1,1),
name varchar(50))

create table owned_by(
sid int,
rid int,
primary key(sid, rid),
foreign key (rid) references Records_Label(rid),
foreign key (sid) references Song(sid))


insert into Artist(name,debut) values('Jon Lajoie', 2007)
insert into Artist(name,debut) values('Carpenter Brut', 2012)
insert into Artist(name,debut) values('Metallica', 1981)
select * from Artist

insert into Album(title, release_year, number_of_tracks) values('You want some of this?', 2009, 17)
insert into Album(title, release_year, number_of_tracks) values('Furi Soundtrack', 2016, 20)
insert into Album(title, release_year, number_of_tracks) values('EP I',2012,6)
insert into Album(title, release_year, number_of_tracks) values('...And justice for all', 1988, 9)
select * from Album

insert into Song(title, length, alid) values ('Everyday normal guy',3.21,1)
insert into Song(title, length, alid) values ('Everyday normal guy 2',3.15,1)
insert into Song(title, length, alid) values ('Enraged',4.58,2)
insert into Song(title, length, alid) values ('You re mine',6.16,2)
insert into Song(title, length, alid) values ('Le Perv',4.18,3)
insert into Song(title, length, alid) values ('Wake up the president',4.19,3)
insert into Song(title, length, alid) values ('One',7.44,4)
insert into Song(title, length, alid) values ('Blackened',6.42,4)
select * from Song

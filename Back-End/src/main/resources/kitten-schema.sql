drop table if exists kitten CASCADE;

create table kitten 
(
	id integer PRIMARY KEY AUTO_INCREMENT, 
	age integer not null, 
	breed varchar(255), 
	cuteness integer not null, 
	full_name varchar(255)
);

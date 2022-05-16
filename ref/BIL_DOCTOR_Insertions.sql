USE [CSC-CSD-S211_10407543]

insert into City values ('9000', 'Aalborg');
insert into City values ('9870', 'Sindal');

insert into Person values ('Joe', 'Banana', '9000', 'Bananavej 42', 'C', '1');
insert into Person values ('Mihal', 'Mihut', '9870', 'Gutenbergvej 2D', 'E', '97845625');

insert into Customer values ('0', '1');

insert into Employee values ('3500', '97845625');

insert into Vehicle values ('AAA', 1, 'Tesla', '1');

insert into Appointment values ('2022-05-14 15:03:42', '0.5', '2022-05-16 10:00:00', 'The car was involved in an accident and now it makes weird noises', '97845625');

insert into Product values ('oil', 101231, 100.0);
insert into Product values ('tire', 5, 400);
insert into Product values ('screws', 40, 10.0);
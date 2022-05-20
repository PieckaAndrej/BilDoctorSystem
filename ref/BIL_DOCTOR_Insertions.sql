USE [CSC-CSD-S211_10407543]

insert into City values ('9000', '+45', 'Aalborg');
insert into City values ('9870', '+45', 'Sindal');

go

insert into Person values ('Joe', 'Banana', '9000', 'Bananavej 42', 'C', '1', '+45');
insert into Person values ('Mihai', 'Mihut', '9870', 'Gutenbergvej 2D', 'E', '97845625', '+45');

go

insert into Discount values('VIP', 20, '2022-04-30');
insert into Discount values('normal', 0, '2022-04-30');

go

insert into Customer values ('VIP', '1', '+45');

go

insert into Employee values ('3002965743','3500', '97845625', '+45', 'idk');

go

insert into Vehicle values ('AAA', 1, 'Tesla', '1', '+45', '2022-05-25');
insert into Vehicle values ('CCC', 12, 'Nokia', '1', '+45', '2022-05-24');
insert into Vehicle values ('BBB', 2, 'Mazda', '1', '+45', '2022-06-26');

go

insert into Appointment values ('2022-05-14 15:03:42', 30, '2022-05-16 10:00:00', 'The car was involved in an accident and now it makes weird noises', '3002965743');

go

insert into Product values ('oil', 101231, 100.0);
insert into Product values ('tire', 5, 400);
insert into Product values ('screws', 40, 10.0);

go
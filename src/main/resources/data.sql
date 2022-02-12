insert into guest(id, name) values(null, 'Gustavo Kuerten');
insert into guest(id, name) values(null, 'N. Djokovic');

insert into tennis_court(id, name) values(null, 'Rod Laver Arena - Melbourne Park,');
insert into tennis_court(id, name) values(null, 'TC2');
insert into tennis_court(id, name) values(null, 'TC3');
insert into tennis_court(id, name) values(null, 'TC4');
insert into tennis_court(id, name) values(null, 'TC5');

insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (null, '2022-10-02T19:00:00.0', '2022-10-02T20:00:00.0', 1);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (2, '2022-02-02T22:00:00.0', '2022-02-02T23:00:00.0', 2);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (3, '2022-05-05T18:00:00.0', '2022-05-05T19:00:00.0', 3);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (4, '2022-10-10T20:00:00.0', '2022-10-10T21:00:00.0', 4);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (5, '2022-12-20T21:00:00.0', '2022-12-20T22:00:00.0', 5);

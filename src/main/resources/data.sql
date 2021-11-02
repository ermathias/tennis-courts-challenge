insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'Wimbledon - All England Lawn Tennis Club');
insert into tennis_court(id, name) values(null, 'US Open - Arthur Ashe Stadium');
insert into tennis_court(id, name) values(null, 'Australian Open - Show Court Arena');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2020-12-20T20:00:00.0', '2020-02-20T21:00:00.0', 1);

insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (null, '2021-12-01T20:20:00.0', '2021-12-01T21:00:00.0', 2);

insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
    (null, '2021-12-30T17:00:00.0', '2021-12-30T18:00:00.0', 3);

insert
into
    reservation
(id, value, RESERVATION_STATUS, SCHEDULE_ID)
values
    (null, 30, 0, 1);
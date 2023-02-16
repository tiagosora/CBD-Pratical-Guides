drop keyspace videoshare;

create keyspace videoshare with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

use videoshare;

create table user (
    username            varchar,
    name                varchar,
    email               varchar,
    registration_date   timestamp,

    primary key (username)
);


create table video (
    id                  uuid,
    author              varchar,
    name                varchar,
    description         varchar,
    tags                set<text>,
    registration_date   timestamp,

    primary key ((author), name)
);

create table comment (
    id                  uuid,
    video               uuid,
    author              varchar,
    comment             varchar,
    registration_date   timestamp,

    primary key ((video), registration_date)
);

create table video_followers (
    video               uuid,
    followers           set<VARCHAR>,

    primary key (video)
);

create table video_event (
    user                VARCHAR,
    video               uuid,
    type                VARCHAR,
    video_time          int,
    registration_date   timestamp,

    primary key ((user,video),registration_date)
);

create table video_rating (
    id                   uuid,
    video                uuid,
    rating               int,

    primary key ((video),id)
);


create index on video(tags);


insert into user (username, name, email, registration_date) values ('lbullon0', 'Lacy Bullon', 'lbullon0@businesswire.com', dateof(now()));
insert into user (username, name, email, registration_date) values ('fshaw1', 'Fae Shaw', 'fshaw1@howstuffworks.com', dateof(now()));
insert into user (username, name, email, registration_date) values ('apithie2', 'Allianora Pithie', 'apithie2@pen.io', dateof(now()));
insert into user (username, name, email, registration_date) values ('ofairburne3', 'Olivia Fairburne', 'ofairburne3@unesco.org', dateof(now()));
insert into user (username, name, email, registration_date) values ('fambrosoni4', 'Flossi Ambrosoni', 'fambrosoni4@com.com', dateof(now()));
insert into user (username, name, email, registration_date) values ('bbardsley5', 'Berny Bardsley', 'bbardsley5@jigsy.com', dateof(now()));
insert into user (username, name, email, registration_date) values ('aslaght6', 'Adelbert Slaght', 'aslaght6@hc360.com', dateof(now()));
insert into user (username, name, email, registration_date) values ('naxtell7', 'Nigel Axtell', 'naxtell7@reuters.com', dateof(now()));
insert into user (username, name, email, registration_date) values ('echeckley8', 'Etty Checkley', 'echeckley8@independent.co.uk', dateof(now()));
insert into user (username, name, email, registration_date) values ('dspier9', 'Darrin Spier', 'dspier9@clickbank.net', dateof(now()));


insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Clevie Dampney', 'Pylance0', 'Sedative, hypnotic or anxiolytic abuse w unsp disorder', {'batatas'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Gaile Penchen', 'Pylance1', 'Ped on rolr-skt inj pk-up truck, pk-up/van nontraf, sequela', {'monster trucks'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Fredra Belamy', 'Pylance2', 'Open wound of scalp', {'diversidade'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Sigismond Stodhart', 'Pylance3', 'Corrosion of unspecified degree of right elbow, subs encntr', {'educação', 'cbd'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Murial McKennan', 'Pylance4', 'Refractory anemia with excess of blasts [RAEB]', {'animais', 'cats'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Marguerite Budgey', 'Pylance5', 'Nondisp fx of 2nd metatarsal bone, l ft, 7thK', {'pessoas', 'comunidades'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Daryle Haycock', 'Pylance6', 'Achilles tendinitis, unspecified leg', {'passa-tempos'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Daniella Roddam', 'Pylance7', 'Corrosion of third degree of foot', {'asmr'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Hammad Hallewell', 'Pylance8', 'Poisoning by other drugs acting on muscles, assault', {'localidades', 'aveiro'}, dateof(now()));
insert into video (id, author, name, description, tags, registration_date) values (uuid(), 'Garnet Laugharne', 'Pylance9', 'Acute dilatation of stomach', {'kids'}, dateof(now()));  



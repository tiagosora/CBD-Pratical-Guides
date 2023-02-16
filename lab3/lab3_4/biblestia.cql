drop keyspace biblestia;

create keyspace biblestia with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

use biblestia;

create table user (
    id                  uuid,
    full_name           varchar,
    email               varchar,
    address             varchar,
    requisitions        list<text>,
    registration_date   timestamp,

    primary key ((id), full_name)
) with clustering order by (full_name desc);

create table employee (
    id                  uuid,
    ssn                 varchar,
    full_name           varchar,
    email               varchar,
    address              varchar,
    registration_date   timestamp,

    primary key ((id), full_name)
) with clustering order by (full_name desc);

create table responsability (
    employee            uuid,
    user                uuid,
    primary key (employee, user)
);

create table book (
    id                  uuid,
    title               varchar,
    authors             set<text>,
    categories          set<text>,
    pag_num             int,
    registration_date   timestamp,

    primary key ((id), title)
) with clustering order by (title desc);

create index on book(categories);


create table collection (
    id                  uuid,
    books_num           int,
    categories          map<text, int>,
    registration_date   timestamp,

    primary key (id)
);

create index on collection(keys(categories));

-- USERS

insert into user (id, full_name, email, address, requisitions, registration_date) values (accc2564-9697-4e27-88f1-f168263d6326, 'Archambault Jarratt', 'ajarratt0@shutterfly.com', '0850 Longview Place', ['Quick and the Dead, The', 'American Pop', 'Julia', 'Quick and the Dead, The'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (be2fa71e-2c6d-4b75-8189-72aafc9f5946, 'Terrye Dunkinson', 'tdunkinson1@netlog.com', '93 Comanche Lane', ['3 Ninjas Kick Back', 'American Pop'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (4eb2bb01-d7b4-421a-9ad6-b73d56dc8323, 'Syd Caustick', 'scaustick2@hubpages.com', '8 Hollow Ridge Plaza', ['Daisies (Sedmikrasky)'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (15ed46da-15a7-4695-8da7-d8488a8f9952, 'Svend Kelson', 'skelson3@unblog.fr', '452 Jenifer Point', ['Quick and the Dead, The', '3 Ninjas Kick Back', 'Daisies (Sedmikrasky)', 'American Pop'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (8b8738eb-4a44-4191-9351-4f9e1bf8a69c, 'Jo-anne Benini', 'jbenini4@reddit.com', '5 Hagan Circle', ['Daisies (Sedmikrasky)'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (d7fb8564-d60e-490f-87ac-b52ada8626f4, 'Corri Milius', 'cmilius5@vinaora.com', '15912 Park Meadow Pass', ['Quick and the Dead, The', 'Blue Chips'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (987f2d38-1e36-456b-bc4b-2b8591e91fac, 'Mart Elstow', 'melstow6@nytimes.com', '800 Orin Road', ['3 Ninjas Kick Back', 'Blue Chips', 'Julia', '3 Ninjas Kick Back'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (57416ce5-8c02-4b26-be93-2ac754d2c2a2, 'Mel Durman', 'mdurman7@ucoz.ru', '15100 Gateway Alley', ['3 Ninjas Kick Back', '3 Ninjas Kick Back', 'Wish You Were Here', 'A Lesson Before Dying'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (977e18ae-5838-4f0a-a944-9a5430ba7745, 'Susan Cullinane', 'scullinane8@slashdot.org', '4233 Arapahoe Drive', ['Don''t Move (Non ti muovere)', 'Julia', 'Julia', 'Julia'], dateof(now()));
insert into user (id, full_name, email, address, requisitions, registration_date) values (4b9ec7ca-7a0d-4534-b1d3-cc67eab5a9aa, 'Margit Ellings', 'mellings9@wp.com', '50496 Hooker Hill', ['Don''t Move (Non ti muovere)', 'Wish You Were Here'], dateof(now()));

-- EMPLOYEES

insert into employee (id, ssn, full_name, email, address, registration_date) values (6aa31aeb-6ecd-4701-8fed-5a86e9f8f78a, '640-53-4274', 'Carry Matthius', 'cmatthius0@slideshare.net', '7 Main Circle', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (52aa9263-0480-4a6f-89bd-d7fb7647919e, '681-49-1316', 'Ara Blueman', 'ablueman1@last.fm', '866 Gerald Terrace', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (4912b128-4aed-4fff-add3-96d41f6c4cc4, '342-62-5677', 'Aldis Tollit', 'atollit2@studiopress.com', '50 Acker Circle', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (e656027f-3785-46e3-9a2d-38098e163ef1, '772-14-6947', 'Pasquale Lucks', 'plucks3@abc.net.au', '60515 Mcbride Center', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (e1eefda0-1658-47e5-bb61-2a287b9386c4, '757-80-7878', 'Amie Wolfenden', 'awolfenden4@illinois.edu', '1 Bay Place', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (b993d85c-34f5-47a4-b24c-50a9ae15349f, '397-13-3655', 'Raimundo Stalman', 'rstalman5@barnesandnoble.com', '30906 Utah Point', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (db77b379-db2c-4842-b83a-895ffba4b4f5, '646-08-9787', 'Francene Serchwell', 'fserchwell6@foxnews.com', '08 Messerschmidt Hill', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (5d5c8c13-745c-4d91-80c6-d3a9297db9dd, '115-13-8193', 'Cole Nellies', 'cnellies7@usgs.gov', '40531 Rowland Hill', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (fe2a431d-5f30-4666-bc58-a9056ca39d0f, '700-83-4618', 'Gwenette Buesnel', 'gbuesnel8@edublogs.org', '397 Park Meadow Plaza', dateof(now()));
insert into employee (id, ssn, full_name, email, address, registration_date) values (3161c9bc-00f4-4149-a58d-03e1831eb6d6, '849-94-3778', 'Adair Chorlton', 'achorlton9@pinterest.com', '9 Lukken Point', dateof(now()));

-- RESPONSABILITY

insert into responsability (employee, user) values (6aa31aeb-6ecd-4701-8fed-5a86e9f8f78a, accc2564-9697-4e27-88f1-f168263d6326);
insert into responsability (employee, user) values (6aa31aeb-6ecd-4701-8fed-5a86e9f8f78a, be2fa71e-2c6d-4b75-8189-72aafc9f5946);
insert into responsability (employee, user) values (52aa9263-0480-4a6f-89bd-d7fb7647919e, 4eb2bb01-d7b4-421a-9ad6-b73d56dc8323);
insert into responsability (employee, user) values (52aa9263-0480-4a6f-89bd-d7fb7647919e, 15ed46da-15a7-4695-8da7-d8488a8f9952);
insert into responsability (employee, user) values (52aa9263-0480-4a6f-89bd-d7fb7647919e, 8b8738eb-4a44-4191-9351-4f9e1bf8a69c);
insert into responsability (employee, user) values (4912b128-4aed-4fff-add3-96d41f6c4cc4, d7fb8564-d60e-490f-87ac-b52ada8626f4);
insert into responsability (employee, user) values (e656027f-3785-46e3-9a2d-38098e163ef1, 987f2d38-1e36-456b-bc4b-2b8591e91fac);
insert into responsability (employee, user) values (e656027f-3785-46e3-9a2d-38098e163ef1, 57416ce5-8c02-4b26-be93-2ac754d2c2a2);
insert into responsability (employee, user) values (e1eefda0-1658-47e5-bb61-2a287b9386c4, 977e18ae-5838-4f0a-a944-9a5430ba7745);
insert into responsability (employee, user) values (e1eefda0-1658-47e5-bb61-2a287b9386c4, 4b9ec7ca-7a0d-4534-b1d3-cc67eab5a9aa);

-- BOOKS

insert into book (id, title, authors, categories, pag_num, registration_date) values (8a50ad26-6f6a-4dca-ae42-8fd52ca2b703, 'Quick and the Dead, The', {'Ardenia Newtown', 'Vern Adey', 'Kym Bottrell'}, {'+18', 'adventure'}, 271, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (5e3b614b-a564-4342-86ad-7d1de54b22eb, '3 Ninjas Kick Back', {'Luz Pordall', 'Benedick Springate'}, {'action', 'adventure', 'thriller'}, 407, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (f623727c-3151-4185-b45f-ff03ce0ee974, 'Daisies (Sedmikrasky)', {'Hatti Pateman', 'Ethelind Quantrill'}, {'+18', 'action'}, 309, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (b5119733-7acf-4295-8b17-b64a2551cd6c, 'Don''t Move (Non ti muovere)', {'Joelie de Clerc', 'Eberto Pearsey'}, {'+18', 'romance'}, 425, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (33252b81-3826-418a-ad65-ec7d5840be50, 'Wish You Were Here', {'Cecilla Sherwin', 'Eberto Pearsey'}, {'+18', 'romance'}, 477, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (714e0f76-472e-48ee-8602-f577f66d0cd2, 'American Pop', {'Murielle Strognell'}, {'adventure', 'thriller'}, 363, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (e163acfe-d76f-4ac7-a6b8-240c86b1f18f, 'Blue Chips', {'Dyane Comellini'}, {'description'}, 254, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (1a24a53b-7050-4a75-a0ff-a038e5b28400, 'A Lesson Before Dying', {'Noellyn Flooks'}, {'description', 'romance'}, 477, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (b57b39d4-dbdc-4482-b310-f0c2cbd44b23, 'Mumia Abu-Jamal: A Case for Reasonable Doubt?', {'Nathan Wiltshire'}, {'+18', 'description'}, 100, dateof(now()));
insert into book (id, title, authors, categories, pag_num, registration_date) values (99101648-2bf2-4369-af41-1660e51c1ff3, 'Julia', {'Hannie Worley'}, {'romance'}, 304, dateof(now()));

-- COLLECTIONS

insert into collection (id, books_num, categories, registration_date) values (5c0f0568-87e5-4c18-84ba-f6fcd6f1bef3, 4, {'+18':2, 'adventure':1, 'action':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (41d161f1-7e49-4f6c-b7e5-60103f627b60, 4, {'+18':2, 'action':2}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (34dae1c8-94e4-4e94-b9e4-c9d91900c88f, 3, {'+18':3}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (1c892e03-6d5f-4df3-a2da-383cad78d4bf, 4, {'+18':1, 'romance':3}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (5148a7ca-30e4-47c1-9d06-504d7e08b78c, 5, {'+18':2, 'romance': 2, 'description':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (0a05f714-a42f-46bd-a8ca-540fb085c2c8, 7, {'adventure':3, 'action':3, 'romance':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (0a4aab66-0b40-450a-a8eb-169bbee38a20, 5, {'action':1, 'romance':4}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (d1bcbdca-3f3b-4835-851a-b9308c46065a, 6, {'adventure':2,'description':1, 'adventure':3}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (74a27ab1-e411-408f-a376-f7f5e750e3a0, 6, {'adventure':2, 'romance':3, 'description':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (1703ec7a-f7ea-47a2-a541-14deb3f08a71, 7, {'action':2, 'description':5}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (adee9744-f5b2-46fb-abe3-91ac2fcfa021, 2, {'action':2}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (7888bffe-b0af-4cca-9fea-48ce9d870917, 3, {'adventure':1, 'thriller':2}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (3826163f-555f-44a6-b180-3739b8c5c7ee, 2, {'romance':2}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (74cce5a5-bc3e-48ce-8a5c-9188861143ed, 1, {'action':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (20f71437-2a3f-42b8-bdff-6a67b309c31d, 4, {'romance':1, 'thriller':3}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (5039c480-bbf3-4278-9f29-3cf3b04a065e, 1, {'romance':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (a24679c3-6495-4e9a-81f6-7a732b075749, 1, {'adventure':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (81f41d11-c4e9-45e0-bfee-355509fa2313, 4, {'action':1, 'thriller':3}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (5d38166e-58b6-4d65-8b2a-133fcfb39aec, 2, {'adventure':1, 'romance':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (4bf43473-eae1-4298-84c9-225f24510356, 1, {'thriller':1}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (8d3612ba-02ca-4ce6-b2d6-5920333b3d7a, 3, {'action':3}, dateof(now()));
insert into collection (id, books_num, categories, registration_date) values (d7772f61-3651-41fc-a58a-44ad7dcf0d06, 7, {'action':1,'thriller':1,'romance':1,'adventure':1,'+18':2,'description':1}, dateof(now()));


-- QUERIES

-- 1. Número de livros para uma coleção especifica
select books_num from collection where id=5c0f0568-87e5-4c18-84ba-f6fcd6f1bef3;

-- 2. Todos os livros com mais de 300 páginas
select * from book where pag_num > 300 allow filtering;

-- 3. Categorias de 6 coleções
select categories from collection limit 6;

-- 4. Os users pelos quais um employee está responsável por ordem decrescente
select * from responsability where employee = 52aa9263-0480-4a6f-89bd-d7fb7647919e order by user desc;

-- 5. Todas as coleçoes com livros do tipo thriller
select * from collection where categories contains key 'thriller';

-- 6. Todos os livros que um utilizador já requisitou
select full_name, requisitions from user where id=15ed46da-15a7-4695-8da7-d8488a8f9952;

-- 7. Todos os utilizadores que já requisitaram um livro
select * from user where requisitions contains 'Quick and the Dead, The' allow filtering;

-- 8. Média de livros das coleções.
select count(id) as Num_Collections, avg(books_num) as Avg_Num_Books from collection;

-- 9. Média de páginas dos livros
select count(id) as Num_Books, avg(pag_num) as Avg_Num_Pags from book;

-- 10. Todas as coleções quem têm pelo apenas 1 livro de action
select * from collection where categories contains key 'action' and categories['action'] = 1 allow filtering;

-- UPDATES

update user set address='Rua da Engenharia Informática' where id = 57416ce5-8c02-4b26-be93-2ac754d2c2a2 and full_name = 'Mel Durman';
update book set authors = authors + {'Autor Desconhecido'} where id = 5e3b614b-a564-4342-86ad-7d1de54b22eb and title = '3 Ninjas Kick Back';
update collection set categories = categories - {'thriller', 'action'} where id = d7772f61-3651-41fc-a58a-44ad7dcf0d06;
update user set requisitions=['Novo Livro da Nova Lista'] where id = 987f2d38-1e36-456b-bc4b-2b8591e91fac and full_name = 'Mart Elstow';
update employee set registration_date='2020-02-02 02:02:02.000000+0000' where id = 3161c9bc-00f4-4149-a58d-03e1831eb6d6 and full_name = 'Adair Chorlton';

-- DELETES

delete email, address from user where id = 4eb2bb01-d7b4-421a-9ad6-b73d56dc8323 and full_name = 'Syd Caustick';
delete from book where id = 1a24a53b-7050-4a75-a0ff-a038e5b28400 and title = 'A Lesson Before Dying';
delete from employee where id = uuid() and full_name = 'Batata' if exists;
delete books_num from collection where id = adee9744-f5b2-46fb-abe3-91ac2fcfa021 if books_num = 5;
delete categories['adventure'] from collection where id = 0a05f714-a42f-46bd-a8ca-540fb085c2c8;
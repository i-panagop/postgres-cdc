create index idx_cat_cdc_parent_cat_id on category_cdc (parent_category_id);
create index idx_cat_cdc_cat_position on category_cdc (category_position);


insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb1426a-086b-11ee-be56-0242ac120002', 'ROOT', 0, null);


insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb14530-086b-11ee-be56-0242ac120002', 'Games', 0, '5cb1426a-086b-11ee-be56-0242ac120002');

insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb14972-086b-11ee-be56-0242ac120002', 'RPG', 0, '5cb14530-086b-11ee-be56-0242ac120002');
insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb14a76-086b-11ee-be56-0242ac120002', 'Strategic', 1, '5cb14530-086b-11ee-be56-0242ac120002');


insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb14666-086b-11ee-be56-0242ac120002', 'Laptops', 1, '5cb1426a-086b-11ee-be56-0242ac120002');

insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb14ee0-086b-11ee-be56-0242ac120002', 'Apple', 0, '5cb14666-086b-11ee-be56-0242ac120002');
insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb15002-086b-11ee-be56-0242ac120002', 'HP', 1, '5cb14666-086b-11ee-be56-0242ac120002');


insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb147c4-086b-11ee-be56-0242ac120002', 'Monitors', 2, '5cb1426a-086b-11ee-be56-0242ac120002');

insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb150fc-086b-11ee-be56-0242ac120002', 'LG', 0, '5cb147c4-086b-11ee-be56-0242ac120002');
insert into category_cdc (id, name, category_position, parent_category_id)
values ('5cb1520a-086b-11ee-be56-0242ac120002', 'HP', 1, '5cb147c4-086b-11ee-be56-0242ac120002');


insert into product_cdc (id, name, description, price, stock, category_id)
values ('5cb15304-086b-11ee-be56-0242ac120002', 'elder ring', 'elder ring', 10.0, 2,
        '5cb14972-086b-11ee-be56-0242ac120002');
insert into product_cdc (id, name, description, price, stock, category_id)
values ('5cb153fe-086b-11ee-be56-0242ac120002', 'civ 6', 'civ 6', 15.0, 4,
        '5cb14a76-086b-11ee-be56-0242ac120002');

insert into product_cdc (id, name, description, price, stock, category_id)
values ('5cb154f8-086b-11ee-be56-0242ac120002', 'macbook pro', 'macbook pro', 4500, 10,
        '5cb14ee0-086b-11ee-be56-0242ac120002');
insert into product_cdc (id, name, description, price, stock, category_id)
values ('5cb157d2-086b-11ee-be56-0242ac120002', 'hp pro book', 'hp pro book', 2500, 4,
        '5cb15002-086b-11ee-be56-0242ac120002');

insert into product_cdc (id, name, description, price, stock, category_id)
values ('5cb158ea-086b-11ee-be56-0242ac120002', 'LG 5000', 'LG 5000', 450, 2,
        '5cb150fc-086b-11ee-be56-0242ac120002');
insert into product_cdc (id, name, description, price, stock, category_id)
values ('5cb159f8-086b-11ee-be56-0242ac120002', 'HP 5000', 'HP 5000', 350.12, 2,
        '5cb1520a-086b-11ee-be56-0242ac120002');
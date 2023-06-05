insert into address_cdc (id, street, street_number, city, country, zip_code)
values ('fad1dbd9-af2b-4a5a-ae31-ba5c64dbb99f', 'street1', '1', 'city1', 'country1', 'zip1');

insert into address_cdc (id, street, street_number, city, country, zip_code)
values ('ba3c0e30-a6e9-4945-91a6-462f83d70ead', 'street2', '2', 'city2', 'country2', 'zip2');

update user_cdc
set address_id = 'fad1dbd9-af2b-4a5a-ae31-ba5c64dbb99f'
where email = 'johndoe@a.com';
update user_cdc
set address_id = 'ba3c0e30-a6e9-4945-91a6-462f83d70ead'
where email = 'kendoe@a.com';
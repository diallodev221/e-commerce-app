CREATE TABLE IF NOT EXISTS category
(
    id INTEGER PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product
(
    id INTEGER PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255),
    available_quantity double precision not null,
    price numeric(30, 2),
    category_id INTEGER
        constraint fk1gdggehehjhfgsjkn references category
);

create sequence  if not exists category_seq increment by 50;
create sequence  if not exists product_seq increment by 50;

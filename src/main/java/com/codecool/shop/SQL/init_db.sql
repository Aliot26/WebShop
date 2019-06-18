ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS pk_products_id CASCADE;
ALTER table IF EXISTS ONLY suppliers DROP CONSTRAINT IF EXISTS pk_suppliers_id CASCADE;
ALTER table IF EXISTS ONLY categories DROP CONSTRAINT IF EXISTS pk_categories_id CASCADE;

ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS fk_suppliers_id CASCADE;
ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS fk_categories_id CASCADE;


DROP TABLE IF EXISTS products;
DROP SEQUENCE IF EXISTS products_id_seq;
create table products(
    id serial primary key,
    name           varchar not null,
    defaultprice   double precision,
    currency varchar,
    description    varchar,
    idcategory     integer,
    idsupplier     integer
);

DROP table IF EXISTS suppliers;
DROP SEQUENCE IF EXISTS suppliers_id_seq;
create table suppliers(
    id serial  primary key,
    name varchar not null,
    description varchar
);

drop table if exists categories;
DROP SEQUENCE IF EXISTS categories_id_seq;
create table categories(
    id serial  primary key,
    name varchar,
    department varchar,
    description varchar
);

ALTER table products ADD CONSTRAINT fk_idsupplier FOREIGN KEY(idsupplier) REFERENCES suppliers(id);
ALTER table products ADD CONSTRAINT fk_idcategory FOREIGN KEY(idcategory) REFERENCES categories(id);

INSERT INTO suppliers VALUES (1,'Amazon', 'Digital content and services');
INSERT INTO suppliers VALUES (2, 'Lenovo', 'Computers');

INSERT INTO categories VALUES(1,'Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO categories VALUES(2, 'Laptop', 'Hardware', 'A personal computer, you need it to code');
INSERT INTO categories VALUES(3, 'Ebook', 'Portable', 'blablabla');

INSERT INTO products VALUES (1, 'Amazon Fire', 49.9, 'USD', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 1, 1);
INSERT INTO products VALUES (2, 'Lenovo IdeaPad Miix 700', 479.9, 'USD', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 1, 2);
INSERT INTO products VALUES (3, 'Amazon Fire HD 8', 89, 'USD', 'Amazon\`s latest Fire HD 8 tablet is a great value for media consumption.', 1, 1);
INSERT INTO products VALUES (4, 'Kindle Paperwhite', 50, 'USD', 'We love Kindle :)', 3, 1);
INSERT INTO products VALUES (5, 'Lenovo Yoga 460', 50, 'USD', 'Convertible business premium laptop)', 2,2);





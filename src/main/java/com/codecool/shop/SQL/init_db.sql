ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS pk_products_id CASCADE;
ALTER table IF EXISTS ONLY suppliers DROP CONSTRAINT IF EXISTS pk_suppliers_id CASCADE;
ALTER table IF EXISTS ONLY categories DROP CONSTRAINT IF EXISTS pk_categories_id CASCADE;

ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS fk_suppliers_id CASCADE;
ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS fk_categories_id CASCADE;


DROP TABLE IF EXISTS products;
create table products
    id serial  not null constraint products_pk primary key AUTO_INCREMENT,
    name           varchar not null,
    defaultprice   double precision,
    currencystring varchar,
    description    varchar,
    idcategory     integer,
    idsupplier     integer
);

DROP table IF EXISTS suppliers
create table suppliers(
    id serial not null constraint suppliers_pk primary key AUTO_INCREMENT,
    name varchar not null,
    description varchar
)

drop table if exists categories
create table categories(
    id serial not null constraint categories_pk primary key AUTO_INCREMENT,
    name varchar,
    department varchar,
    description varchar
)

ALTER table products ADD CONSTRAINT fk_idsupplier FOREIGN KEY(idsupplier) REFERENCES suppliers(id);
ALTER table products ADD CONSTRAINT fk_idcategory FOREIGN KEY(idcategory) REFERENCES categories(id);

INSERT INTO suppliers VALUES ('Amazon', 'Digital content and services');
INSERT INTO suppliers VALUES ('Lenovo', 'Computers');

INSERT INTO categories VALUES('Tablet', 'Hardware', 'A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.');
INSERT INTO categories VALUES('Laptop', 'Hardware', 'A personal computer, you need it to code');
INSERT INTO categories VALUES('Ebook', 'Portable', 'blablabla');

INSERT INTO products VALUES ('Amazon Fire', 49.9, 'USD', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 1, 1);
INSERT INTO products VALUES ('Lenovo IdeaPad Miix 700', 479.9, 'USD', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 1, 1);
INSERT INTO products VALUES ('Amazon Fire HD 8', 89, 'USD', 'Amazon\`s latest Fire HD 8 tablet is a great value for media consumption.', 1, 1);
INSERT INTO products VALUES ('Kindle Paperwhite', 50, 'USD', 'We love Kindle :)', 1, 1);





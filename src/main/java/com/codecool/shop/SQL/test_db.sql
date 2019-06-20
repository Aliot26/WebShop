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
ALTER SEQUENCE products_id_seq OWNED BY products.id;


DROP table IF EXISTS suppliers;
DROP SEQUENCE IF EXISTS suppliers_id_seq;
create table suppliers(
                          id serial  primary key,
                          name varchar not null,
                          description varchar
);
ALTER SEQUENCE suppliers_id_seq OWNED BY suppliers.id;

drop table if exists categories;
DROP SEQUENCE IF EXISTS categories_id_seq;
create table categories(
                           id serial  primary key,
                           name varchar,
                           department varchar,
                           description varchar
);
ALTER SEQUENCE categories_id_seq OWNED BY categories.id;


ALTER table products ADD CONSTRAINT fk_idsupplier FOREIGN KEY(idsupplier) REFERENCES suppliers(id);
ALTER table products ADD CONSTRAINT fk_idcategory FOREIGN KEY(idcategory) REFERENCES categories(id);

SELECT setval(pg_get_serial_sequence('suppliers', 'id'), coalesce(max(id),0) +1, false) FROM suppliers;
SELECT setval(pg_get_serial_sequence('categories', 'id'), coalesce(max(id),0) +1, false) FROM categories;
SELECT setval(pg_get_serial_sequence('products', 'id'), coalesce(max(id),0) +1, false) FROM products;

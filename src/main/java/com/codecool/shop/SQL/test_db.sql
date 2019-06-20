ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS pk_products_id CASCADE;
ALTER table IF EXISTS ONLY suppliers DROP CONSTRAINT IF EXISTS pk_suppliers_id CASCADE;
ALTER table IF EXISTS ONLY categories DROP CONSTRAINT IF EXISTS pk_categories_id CASCADE;

ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS fk_suppliers_id CASCADE;
ALTER table IF EXISTS ONLY products DROP CONSTRAINT IF EXISTS fk_categories_id CASCADE;




DROP table IF EXISTS suppliers CASCADE ;
DROP SEQUENCE IF EXISTS suppliers_id_seq;

create table suppliers(
                          id serial  primary key,
                          name varchar not null,
                          description varchar
);

ALTER SEQUENCE suppliers_id_seq  OWNED BY suppliers.id;


drop table if exists categories CASCADE ;
DROP SEQUENCE IF EXISTS categories_id_seq;
create table categories(
                           id serial  primary key,
                           name varchar,
                           department varchar,
                           description varchar
);

ALTER SEQUENCE categories_id_seq  OWNED BY categories.id;



DROP TABLE IF EXISTS products CASCADE ;
DROP SEQUENCE IF EXISTS products_id_seq;
create table products(
                         id serial primary key,
                         name           varchar not null,
                         defaultprice   double precision,
                         currency varchar,
                         description    varchar,
                         idcategory     INTEGER REFERENCES categories(id),
                         idsupplier     INTEGER REFERENCES suppliers(id)
);

ALTER SEQUENCE products_id_seq   OWNED BY products.id;
-- ALTER table products ADD CONSTRAINT fk_idsupplier FOREIGN KEY(idsupplier) REFERENCES suppliers(id);
-- ALTER table products ADD CONSTRAINT fk_idcategory FOREIGN KEY(idcategory) REFERENCES categories(id);



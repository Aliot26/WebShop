DROP TABLE IF EXISTS products;
create table products
(
    id             serial  not null
        constraint products_pk
            primary key,
    name           varchar not null,
    defaultprice   double precision,
    currencystring varchar,
    description    varchar,
    idcategory     integer,
    idsupplier     integer
);

alter table products
    owner to kacper;

create unique index products_id_uindex
    on products (id);



INSERT INTO public.products ( name, defaultprice, currencystring, description, idcategory, idsupplier) VALUES ('Amazon Fire', 49.9, 'USD', 'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 1, 1);
INSERT INTO public.products ( name, defaultprice, currencystring, description, idcategory, idsupplier) VALUES ('Lenovo IdeaPad Miix 700', 479.9, 'USD', 'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 1, 1);
INSERT INTO public.products ( name, defaultprice, currencystring, description, idcategory, idsupplier) VALUES ('Amazon Fire HD 8', 89, 'USD', 'Amazon\`s latest Fire HD 8 tablet is a great value for media consumption.', 1, 1);
INSERT INTO public.products (name, defaultprice, currencystring, description, idcategory, idsupplier) VALUES ('Kindle Paperwhite', 50, 'USD', 'We love Kindle :)', 1, 1);


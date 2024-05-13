CREATE TABLE if NOT EXISTS beer
(
    id             integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    beer_name      varchar(255),
    beer_style     varchar (255),
    upc            varchar (25),
    quantity_on_hand integer,
    price          decimal(16,2),
    created_at   datetime(6),
    updated_at   datetime(6)
);

CREATE TABLE if NOT EXISTS customer
(
    id             integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name           varchar(255),
    created_at   datetime(6),
    updated_at   datetime(6)
);

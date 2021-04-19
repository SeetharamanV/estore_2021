create table items (
  id bigint auto_increment  primary key,
  name varchar(250) not null,
  category varchar(250) not null,
  description varchar(250) not null,
  brand varchar(250) not null,
  barcode varchar(250) not null,
  price decimal(10, 2) not null CHECK(price > 0),
  CONSTRAINT uc_item UNIQUE (name)
);
create index idx_item_category on items (category);
create index idx_item_brand on items (brand);
create index idx_item_barcode on items (barcode);
create index idx_item_price on items (price);
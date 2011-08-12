create table crm.customer (
  id bigint not null auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  primary key (id)
);

create table crm.line_item (
  id bigint not null auto_increment,
  shipped date,
  purchased date,
  product_id bigint not null,
  purchase_id bigint not null,
  primary key (id),
  foreign key line_item_ibfk_2 (purchase_id) references purchase(id),
  foreign key line_item_ibfk_1 (product_id) references product(id)
);
CREATE INDEX product_id ON crm.line_item (product_id);
CREATE INDEX purchase_id ON crm.line_item (purchase_id);

create table crm.product (
  id bigint not null auto_increment,
  description varchar(255),
  name varchar(255) not null,
  price double(22, 0) not null,
  primary key (id)
);

create table crm.purchase (
  id bigint not null auto_increment,
  total double(22, 0) not null,
  frozen BIT,
  customer_id bigint not null,
  primary key (id),
  foreign key purchase_ibfk_1 (customer_id) references customer(id)
);
CREATE INDEX customer_id ON crm.purchase (customer_id);

create table crm.purchase_fulfillment_log (
  id bigint not null auto_increment,
  purchase_id bigint not null,
  customer_id bigint not null,
  event varchar(255) not null,
  event_date date,
  primary key (id),
  foreign key purchase_fulfillment_log_ibfk_2 (customer_id) references customer(id),
  foreign key purchase_fulfillment_log_ibfk_1 (purchase_id) references purchase(id)
);
CREATE INDEX purchase_id ON crm.purchase_fulfillment_log (purchase_id);
CREATE INDEX customer_id ON crm.purchase_fulfillment_log (customer_id);


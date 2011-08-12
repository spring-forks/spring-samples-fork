--
-- CRM DDL
--

-- basic table definitions
CREATE TABLE product(
  id bigint,
  description VARCHAR(255),
  `name` VARCHAR(255) NOT NULL,
  price DOUBLE PRECISION NOT NULL
);


CREATE TABLE customer(
  id bigint,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL
);

CREATE TABLE purchase(
  id bigint,
  total DOUBLE PRECISION NOT NULL,
  frozen BOOLEAN,
  customer_id BIGINT NOT NULL
);


CREATE TABLE line_item(
  id bigint,
  shipped  DATE NULL,
  purchased DATE NULL,
  product_id BIGINT NOT NULL,
  purchase_id BIGINT NOT NULL
);

CREATE TABLE purchase_fulfillment_log(
  id bigint,
  purchase_id bigint NOT NULL,
  customer_id bigint NOT NULL,
  event VARCHAR(255) NOT NULL,
  event_date DATE
);


-- define primary keys
ALTER TABLE crm.customer MODIFY COLUMN id BIGINT(20)  DEFAULT NULL AUTO_INCREMENT,  ADD PRIMARY KEY (id);
ALTER TABLE crm.line_item MODIFY COLUMN id BIGINT(20)  DEFAULT NULL AUTO_INCREMENT,  ADD PRIMARY KEY (id);
ALTER TABLE crm.product MODIFY COLUMN id BIGINT(20)  DEFAULT NULL AUTO_INCREMENT,  ADD PRIMARY KEY (id);
ALTER TABLE crm.purchase MODIFY COLUMN id BIGINT(20)  DEFAULT NULL AUTO_INCREMENT,  ADD PRIMARY KEY (id);
ALTER TABLE crm.purchase_fulfillment_log MODIFY COLUMN id BIGINT(20)  DEFAULT NULL AUTO_INCREMENT, ADD PRIMARY KEY (id);

alter table  purchase_fulfillment_log add foreign key (purchase_id) references purchase(id);
alter table  purchase_fulfillment_log add foreign key (customer_id) references customer(id);
alter table line_item add foreign key (product_id) references product(id);
alter table line_item add foreign key (purchase_id ) references purchase(id);
alter table purchase add foreign key (customer_id) references customer(id);

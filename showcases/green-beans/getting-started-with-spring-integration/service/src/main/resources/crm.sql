-- <!--
-- /*
-- * Copyright 2006-2007 the original author or authors.
-- *
-- * Licensed under the Apache License, Version 2.0 (the "License");
-- * you may not use this file except in compliance with the License.
-- * You may obtain a copy of the License at
-- *
-- *      http://www.apache.org/licenses/LICENSE-2.0
-- *
-- * Unless required by applicable law or agreed to in writing, software
-- * distributed under the License is distributed on an "AS IS" BASIS,
-- * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- * See the License for the specific language governing permissions and
-- * limitations under the License.
-- */
--


--
-- CRM DDL
--

-- basic table definitions
CREATE TABLE product (
  id SERIAL,
  description VARCHAR(255),
  name VARCHAR(255) NOT NULL,
  price DOUBLE PRECISION NOT NULL
);


CREATE TABLE customer (
  id serial,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL
);

CREATE TABLE purchase (
  id serial ,
  total DOUBLE PRECISION NOT NULL,
  frozen BOOLEAN,
  customer_id BIGINT NOT NULL
);


CREATE TABLE line_item (
  id serial,
  shipped  DATE NULL,
  purchased DATE NULL,
  product_id BIGINT NOT NULL,
  purchase_id BIGINT NOT NULL
);

CREATE TABLE purchase_fulfillment_log (
  purchase_fulfillment_log_id serial,
  purchase_id bigint NOT NULL,
  customer_id bigint NOT NULL,
  event VARCHAR(255) NOT NULL,
  event_date DATE
);


alter table purchase_fulfillment_log add foreign key (purchase_id ) references purchase (id);
alter table purchase_fulfillment_log add foreign key (customer_id ) references customer (id);

alter table line_item add foreign key (product_id) references product(id);

alter table line_item add foreign key (purchase_id ) references purchase (id);

alter table purchase add foreign key(customer_id ) references customer (id);



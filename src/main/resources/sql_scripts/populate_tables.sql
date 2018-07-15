USE abcoline
-- Customers
insert into customers (date_of_birth, email_address, first_name, last_name, id) values  ("1988-07-22", "abconline@gmail.com", "ABC", "Online", 67);
insert into customers (date_of_birth, email_address, first_name, last_name, id) values  ("1986-07-22", "xyz@gmail.com", "XYZ", "Inc", 68);
insert into customers (date_of_birth, email_address, first_name, last_name, id) values  ("1986-07-22", "euedofia@gmail.com", "Etimbuk", "Udofia", 78);
insert into customers (date_of_birth, email_address, first_name, last_name, id) values  ("1936-01-01", "info@clearchannel.co.uk", "Clear", "Channel UK", 69);

-- order_items
insert into order_items (item_id, item_quantity, item_name, item_sku, item_description, item_price, date_of_purchase) values (777, 2, "Effective Java", "effective-java-3rd-ed", "Java best practices", "67 GBP", "2018-07-14");
insert into order_items (item_id, item_quantity, item_name, item_sku, item_description, item_price, date_of_purchase) values (767, 1, "Java Concurrency In Practice", "java-concurrency-ed", "Java concurrency & threading best practices", "45 GBP", "2018-07-14");
insert into order_items (item_id, item_quantity, item_name, item_sku, item_description, item_price, date_of_purchase) values (797, 2, "Java Concurrency In Practice", "java-concurrency-ed", "Java concurrency & threading best practices", "90 GBP", "2018-07-14");
insert into order_items (item_id, item_quantity, item_name, item_sku, item_description, item_price, date_of_purchase) values (757, 4, "Effective Java", "effective-java-3rd-ed", "Java best practices", "100 GBP", "2018-07-14");

-- Baskets
insert into baskets (id, created_at, updated_at, customer_id, order_items_item_id) values(999, "2018-07-14", "2018-07-15", 67, 777);
insert into baskets (id, created_at, updated_at, customer_id, order_items_item_id) values(979, "2018-07-14", "2018-07-15", 67, 797);
insert into baskets (id, created_at, updated_at, customer_id, order_items_item_id) values(969, "2018-07-14", "2018-07-15", 69, 797);
insert into baskets (id, created_at, updated_at, customer_id, order_items_item_id) values(989, "2018-06-13", "2018-07-15", 68, 767);
insert into baskets (id, created_at, updated_at, order_items_item_id) values(929, "2018-07-14", "2018-07-15", 797);

-- orders
insert into orders (id, order_items_item_id, customers_id, created_on, updated_on) values(333, 777, 67, "2018-07-14", "2018-07-15");
insert into orders (id, order_items_item_id, customers_id, created_on, updated_on) values(343, 767, 67, "2018-07-14", "2018-07-15");
insert into orders (id, order_items_item_id, customers_id, created_on, updated_on) values(353, 797, 67, "2018-07-14", "2018-07-15");
insert into orders (id, order_items_item_id, customers_id, created_on, updated_on) values(363, 757, 78, "2018-07-14", "2018-07-15");
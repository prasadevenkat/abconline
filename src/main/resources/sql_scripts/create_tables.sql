use abcoline;
create table order_items (
		item_id bigint not null auto_increment,
        date_of_purchase varchar(255),
        item_description varchar(255),
        item_name varchar(255),
        item_price varchar(255),
        item_quantity integer,
        item_sku varchar(255),
        primary key (item_id)
);

create table customers (
			id bigint not null auto_increment,
            date_of_birth datetime,
            email_address varchar(255),
            first_name varchar(255),
            last_name varchar(255),
            primary key (id)
);

create table baskets (
		id bigint not null auto_increment,
        created_at datetime,
        updated_at datetime,
        customer_id bigint,
        order_items_item_id bigint,
        primary key (id)
        -- foreign key (customer_id) references customers(id),
        -- foreign key (order_items_item_id) references order_items(id)
);

create table orders (
		id bigint not null auto_increment,
        created_on datetime,
        updated_on datetime,
        customers_id bigint,
        order_items_item_id bigint,
        primary key (id)
        -- foreign key (customers_id) references customers(id),
        -- foreign key (order_items_item_id) references order_items(id)
);

alter table baskets add constraint fk_customer_order_items foreign key (customer_id) references customers(id);
alter table baskets add constraint fk_order_order_items_basket foreign key (order_items_item_id) references order_items(item_id);
alter table orders add constraint fk_customer_orders foreign key (customers_id) references customers(id);
alter table orders add constraint fk_orders_order_item foreign key (order_items_item_id) references order_items(item_id);
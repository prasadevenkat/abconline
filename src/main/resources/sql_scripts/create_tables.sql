use abconline;
create table baskets (
      id bigint not null auto_increment,
       created_at date,
       updated_at date,
       customer_id bigint,
       primary key (id)
) engine=InnoDB;

create table customers (
      id bigint not null auto_increment,
      date_of_birth date,
       email_address varchar(255),
       first_name varchar(255),
       last_name varchar(255),
       primary key (id)
) engine=InnoDB;

create table order_items (
      item_id bigint not null auto_increment,
      date_of_purchase date,
      item_description varchar(255),
      item_name varchar(255),
      item_price varchar(255),
      item_quantity integer,
      item_sku varchar(255),
      basket_id bigint,
      order_id bigint,
      primary key (item_id)
) engine=InnoDB;

create table orders (
      id bigint not null auto_increment,
      created_on date, updated_on date,
      customer_id bigint,
      primary key (id)
) engine=InnoDB;

alter table baskets add constraint FKd9n51h863f9df2m0js5v3slv4 foreign key (customer_id) references customers (id);
alter table order_items add constraint FK962j9tb2yhha2dg7yd6hyaey6 foreign key (basket_id) references baskets (id);
alter table order_items add constraint FKbioxgbv59vetrxe0ejfubep1w foreign key (order_id) references orders (id);
alter table orders add constraint FKpxtb8awmi0dk6smoh2vp1litg foreign key (customer_id) references customers (id);
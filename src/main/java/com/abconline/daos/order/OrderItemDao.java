package com.abconline.daos.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.order.OrderItem;

public interface OrderItemDao extends JpaRepository<OrderItem, Long> {

}
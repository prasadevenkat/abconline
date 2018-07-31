package com.abconline.daos.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.order.Order;

public interface OrdersDao extends JpaRepository<Order, Long> {
  Order findByCustomerId(Long customerId);
}
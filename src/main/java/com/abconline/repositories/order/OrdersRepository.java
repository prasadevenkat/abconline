package com.abconline.repositories.order;

import com.abconline.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {
  Order findByCustomerId(Long customerId);
}
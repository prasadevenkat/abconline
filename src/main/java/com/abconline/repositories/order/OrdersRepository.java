package com.abconline.repositories.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.order.Order;

public interface OrdersRepository extends JpaRepository<Order, Long> {
  List<Order> findByCustomerId(Long customerId);
  void deleteByCustomerId(Long customerId);
}
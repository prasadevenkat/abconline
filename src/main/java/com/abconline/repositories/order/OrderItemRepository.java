package com.abconline.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
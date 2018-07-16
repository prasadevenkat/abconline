package com.abconline.daos.basket;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.basket.Basket;

public interface BasketDao extends JpaRepository<Basket, Long> {

  void deleteByCustomerId(Long customer);

}

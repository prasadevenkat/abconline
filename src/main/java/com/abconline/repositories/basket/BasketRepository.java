package com.abconline.repositories.basket;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.basket.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {

  void deleteByCustomerId(Long customer);

}

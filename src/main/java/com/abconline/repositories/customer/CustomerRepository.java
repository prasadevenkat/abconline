package com.abconline.repositories.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
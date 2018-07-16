package com.abconline.daos.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abconline.models.customer.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
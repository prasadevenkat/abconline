package com.abconline.controllers.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abconline.models.customer.Customer;
import com.abconline.repositories.customer.CustomerRepository;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

  @Autowired
  private final CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @GetMapping
  public ResponseEntity<List<Customer>> list() {

    if (customerRepository.findAll().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(new ArrayList<>(customerRepository.findAll()), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Customer customer) {
    if (customer != null) {
      customerRepository.save(customer);
      return new ResponseEntity<>(HttpStatus.CREATED);

    }

    return new ResponseEntity<>("Unable to create Customer due to empty request payload",
        HttpStatus.PRECONDITION_FAILED);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> get(@PathVariable("id") long id) {
    if (customerRepository.findById(id).isPresent()) {
      return new ResponseEntity<>(customerRepository.getOne(id), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
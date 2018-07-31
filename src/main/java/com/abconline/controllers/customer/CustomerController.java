package com.abconline.controllers.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abconline.daos.customer.CustomerDao;
import com.abconline.models.customer.Customer;

import static com.abconline.utils.AbcOnlineStrings.RESPONSE_MESSAGE_KEY;
import static com.abconline.utils.AbcOnlineStrings.STATUS_KEY;
import static com.abconline.utils.AbcOnlineStrings.SUCCESS_KEY;

@RestController
@RequestMapping(value = "/customers/")
public class CustomerController {

  private final CustomerDao customerDao;

  public CustomerController(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  @Autowired


  @GetMapping
  public ResponseEntity<List<Customer>> list() {

    if (customerDao.findAll().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(new ArrayList<>(customerDao.findAll()), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Customer customer) {
    if (customer != null) {
      customerDao.save(customer);
      return new ResponseEntity<>(HttpStatus.CREATED);

    }

    return new ResponseEntity<>("Unable to create Customer due to empty request payload",
        HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Customer> get(@PathVariable("id") long id) {
    if (customerDao.findById(id).isPresent()) {
      return new ResponseEntity<>(customerDao.getOne(id), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping(value = "/{customerId}")
  public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") long customerId) {
    if (customerDao.existsById(customerId)) {
      //now delete customer
      customerDao.deleteById(customerId);

      Map<String, String> response = new HashMap<>();
      response.put(STATUS_KEY, SUCCESS_KEY);
      response.put(RESPONSE_MESSAGE_KEY, String.format("Successfully deleted customer with id %1$s",
          customerId));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
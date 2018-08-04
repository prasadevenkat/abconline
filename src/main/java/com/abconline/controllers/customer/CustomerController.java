package com.abconline.controllers.customer;

import com.abconline.daos.basket.BasketDao;
import com.abconline.daos.customer.CustomerDao;
import com.abconline.daos.order.OrderItemDao;
import com.abconline.daos.order.OrdersDao;
import com.abconline.models.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.abconline.utils.AbcOnlineStrings.*;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

  private final CustomerDao customerDao;
  private final OrderItemDao orderItemDao;
  private final OrdersDao ordersDao;
  private final BasketDao basketDao;

  @Autowired
  public CustomerController(CustomerDao customerDao, OrderItemDao orderItemDao,
                            OrdersDao ordersDao, BasketDao basketDao) {
    this.customerDao = customerDao;
    this.orderItemDao = orderItemDao;
    this.ordersDao = ordersDao;
    this.basketDao = basketDao;
  }

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

      if (Objects.nonNull(customer.getBasket())) {
        basketDao.save(customer.getBasket());
      }

      if (!CollectionUtils.isEmpty(customer.getOrders())) {
        customer.getOrders().forEach(order -> orderItemDao.saveAll(order.getItems()));
        ordersDao.saveAll(customer.getOrders());
      }

      Customer savedCustomer = customerDao.save(customer);
      return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);

    }

    return new ResponseEntity<>("Unable to create Customer due to empty request payload",
        HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/{customerId}")
  public ResponseEntity<Customer> get(@PathVariable("customerId") long customerId) {
    if (customerDao.findById(customerId).isPresent()) {
      return new ResponseEntity<>(customerDao.getOne(customerId), HttpStatus.OK);
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
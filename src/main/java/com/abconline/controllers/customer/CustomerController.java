package com.abconline.controllers.customer;

import com.abconline.models.customer.Customer;
import com.abconline.repositories.basket.BasketRepository;
import com.abconline.repositories.customer.CustomerRepository;
import com.abconline.repositories.order.OrderItemRepository;
import com.abconline.repositories.order.OrdersRepository;
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

  private final CustomerRepository customerRepository;
  private final OrderItemRepository orderItemRepository;
  private final OrdersRepository ordersRepository;
  private final BasketRepository basketRepository;

  @Autowired
  public CustomerController(CustomerRepository customerRepository, OrderItemRepository orderItemRepository,
                            OrdersRepository ordersRepository, BasketRepository basketRepository) {
    this.customerRepository = customerRepository;
    this.orderItemRepository = orderItemRepository;
    this.ordersRepository = ordersRepository;
    this.basketRepository = basketRepository;
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

      if (Objects.nonNull(customer.getBasket())) {
        basketRepository.save(customer.getBasket());
      }

      if (!CollectionUtils.isEmpty(customer.getOrders())) {
        customer.getOrders().forEach(order -> orderItemRepository.saveAll(order.getItems()));
        ordersRepository.saveAll(customer.getOrders());
      }

      Customer savedCustomer = customerRepository.save(customer);
      return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);

    }

    return new ResponseEntity<>("Unable to create Customer due to empty request payload",
        HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/{customerId}")
  public ResponseEntity<Customer> get(@PathVariable("customerId") long customerId) {
    if (customerRepository.findById(customerId).isPresent()) {
      return new ResponseEntity<>(customerRepository.getOne(customerId), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping(value = "/{customerId}")
  public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") long customerId) {
    if (customerRepository.existsById(customerId)) {
      //now delete customer
      customerRepository.deleteById(customerId);

      Map<String, String> response = new HashMap<>();
      response.put(STATUS_KEY, SUCCESS_KEY);
      response.put(RESPONSE_MESSAGE_KEY, String.format("Successfully deleted customer with id %1$s",
          customerId));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
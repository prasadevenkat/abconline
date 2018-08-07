package com.abconline.controllers.orders;

import com.abconline.models.order.Order;
import com.abconline.models.order.OrderItem;
import com.abconline.repositories.order.OrdersRepository;
import com.abconline.utils.AbcOnlineStrings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.abconline.utils.AbcOnlineStrings.*;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

  private final OrdersRepository ordersRepository;

  public OrdersController(OrdersRepository ordersRepository) {
    this.ordersRepository = ordersRepository;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Order order) {
    if (Objects.isNull(order)) {
      return new ResponseEntity<>(EMPTY_PAYLOAD_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    if (CollectionUtils.isEmpty(order.getItems())) {
      return new ResponseEntity<>("Cannot create orders without order items.", HttpStatus.BAD_REQUEST);
    }

    Order savedOrder;

    if (Objects.nonNull(order.getCustomer())) {
      savedOrder = ordersRepository.save(new Order(order.getCreatedOn(), order.getUpdatedOn(), order.getItems()));

    } else {
      savedOrder = ordersRepository.save(new Order(order.getCreatedOn(), order.getUpdatedOn(), order.getItems(), order.getCustomer()));
    }

    Map<String, String> responsePayload = new HashMap<>();
    responsePayload.put(AbcOnlineStrings.STATUS_KEY, SUCCESS_KEY);
    responsePayload.put(RESPONSE_MESSAGE_KEY, String.format("Successfully created order %1$s", savedOrder.getId()));

    return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Order>> list() {
    if (ordersRepository.findAll().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(new ArrayList<>(ordersRepository.findAll()), HttpStatus.OK);
  }

  @GetMapping(value = "{orderId}")
  public ResponseEntity<?> getOrder(@PathVariable("orderId") long orderId) {
    if (ordersRepository.findById(orderId).isPresent()) {
      Order anOrder = ordersRepository.getOne(orderId);

      Map<String, List<OrderItem>> mappedOrderItems = new HashMap<>();
      mappedOrderItems.put(ORDERS_KEY, new ArrayList<>(anOrder.getItems()));

      return new ResponseEntity<>(mappedOrderItems, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(value = "{orderId}/pay")
  public ResponseEntity<?> processPayment(@PathVariable("orderId") long orderId) {
    if (ordersRepository.findById(orderId).isPresent()) {
      /*
      This is just for demo -- we can plug this into a payment platform like stripe.
      For now we just return a 200
       */
      Map<String, String> response = new HashMap<>();
      response.put(STATUS_KEY, SUCCESS_KEY);
      response.put(RESPONSE_MESSAGE_KEY, String.format("Successfully simulated payment for order %1$s",
          orderId));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping(value = "/customer/{customerId}")
  public ResponseEntity<?> customerOrders(@PathVariable("customerId") long customerId) {
    // does customerId have any orders?
    if (Objects.nonNull(ordersRepository.findByCustomerId(customerId))) {
      Order customerOrder = ordersRepository.findByCustomerId(customerId);

      Map<String, List<OrderItem>> mappedCustomerOrders = new HashMap<>();
      mappedCustomerOrders.put("orders", customerOrder.getItems());

      return new ResponseEntity<>(mappedCustomerOrders, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
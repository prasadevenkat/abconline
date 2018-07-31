package com.abconline.controllers.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abconline.daos.order.OrdersDao;
import com.abconline.models.order.Order;
import com.abconline.models.order.OrderItem;
import com.abconline.utils.AbcOnlineStrings;

import static com.abconline.utils.AbcOnlineStrings.EMPTY_PAYLOAD_MESSAGE;
import static com.abconline.utils.AbcOnlineStrings.ORDERS_KEY;
import static com.abconline.utils.AbcOnlineStrings.RESPONSE_MESSAGE_KEY;
import static com.abconline.utils.AbcOnlineStrings.STATUS_KEY;
import static com.abconline.utils.AbcOnlineStrings.SUCCESS_KEY;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

  private final OrdersDao ordersDao;

  public OrdersController(OrdersDao ordersDao) {
    this.ordersDao = ordersDao;
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
      savedOrder = ordersDao.save(new Order(order.getCreatedOn(), order.getUpdatedOn(), order.getItems()));

    } else {
      savedOrder = ordersDao.save(new Order(order.getCreatedOn(), order.getUpdatedOn(), order.getItems(), order.getCustomer()));
    }

    Map<String, String> responsePayload = new HashMap<>();
    responsePayload.put(AbcOnlineStrings.STATUS_KEY, SUCCESS_KEY);
    responsePayload.put(RESPONSE_MESSAGE_KEY, String.format("Successfully created order %1$s", savedOrder.getId()));

    return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Order>> list() {
    if (ordersDao.findAll().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(new ArrayList<>(ordersDao.findAll()), HttpStatus.OK);
  }

  @GetMapping(value = "{orderId}")
  public ResponseEntity<?> getOrder(@PathVariable("orderId") long orderId) {
    if (ordersDao.findById(orderId).isPresent()) {
      Order anOrder = ordersDao.getOne(orderId);

      Map<String, List<OrderItem>> mappedOrderItems = new HashMap<>();
      mappedOrderItems.put(ORDERS_KEY, new ArrayList<>(anOrder.getItems()));

      return new ResponseEntity<>(mappedOrderItems, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(value = "{orderId}/pay")
  public ResponseEntity<?> processPayment(@PathVariable("orderId") long orderId) {
    if (ordersDao.findById(orderId).isPresent()) {
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
    if (Objects.nonNull(ordersDao.findByCustomerId(customerId))) {
      Order customerOrder = ordersDao.findByCustomerId(customerId);

      Map<String, List<OrderItem>> mappedCustomerOrders = new HashMap<>();
      mappedCustomerOrders.put("orders", customerOrder.getItems());

      return new ResponseEntity<>(mappedCustomerOrders, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
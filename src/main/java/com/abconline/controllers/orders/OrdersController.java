package com.abconline.controllers.orders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abconline.daos.customer.CustomerDao;
import com.abconline.daos.order.OrderItemDao;
import com.abconline.daos.order.OrdersDao;
import com.abconline.models.order.Order;
import com.abconline.models.order.OrderItem;
import com.abconline.utils.AbcOnlineStrings;

import static com.abconline.utils.AbcOnlineStrings.ORDERS_KEY;
import static com.abconline.utils.AbcOnlineStrings.RESPONSE_MESSAGE_KEY;
import static com.abconline.utils.AbcOnlineStrings.STATUS_KEY;
import static com.abconline.utils.AbcOnlineStrings.SUCCESS_KEY;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

  private final OrdersDao ordersDao;
  private final CustomerDao customerDao;
  private final OrderItemDao orderItemDao;

  public OrdersController(OrdersDao ordersDao, CustomerDao customerDao,
      OrderItemDao orderItemDao) {
    this.ordersDao = ordersDao;
    this.customerDao = customerDao;
    this.orderItemDao = orderItemDao;
  }

  @PostMapping(value = "/add/{customerId}")
  public ResponseEntity<?> create(@PathVariable("customerId") long customerId, @RequestBody OrderItem order) {
    // does the customerId exist?
    if (!customerDao.findById(customerId).isPresent()) {
      return new ResponseEntity<>(String.format("Unable to proceed due to unknown customerId value %1$s.", customerId), HttpStatus.UNAUTHORIZED);
    }

    if (Objects.nonNull(order)) {
      LocalDate now = LocalDate.now();

      order.setDateOfPurchase(now);
      OrderItem savedOrderItem = orderItemDao.save(order);

      Order savedOrderInfo = ordersDao.save(new Order(savedOrderItem.getItemId(), customerId, now));

      Map<String, String> responsePayload = new HashMap<>();
      responsePayload.put(AbcOnlineStrings.STATUS_KEY, SUCCESS_KEY);
      responsePayload.put(RESPONSE_MESSAGE_KEY, String.format("Successfully created order %1$s for customer with id %2$s",
          savedOrderInfo.getId(), customerId));

      return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
    }

    return new ResponseEntity<>("Unable to create an Order due to empty request payload",
        HttpStatus.BAD_REQUEST);
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
      mappedOrderItems.put(ORDERS_KEY, new ArrayList<>(orderItemDao.findAllById(Collections
          .singleton(anOrder.getOrderItemsItemId()))));

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
    if (!ordersDao.findByCustomerId(customerId).isEmpty()) {
      List<Order> customerOrders = new ArrayList<>(ordersDao.findByCustomerId(customerId));

      List<Long> orderItemsIds = customerOrders.stream().map(Order::getOrderItemsItemId)
          .collect(Collectors.toList());


      List<OrderItem> orderItems = orderItemDao.findAllById(orderItemsIds);

      Map<String, List<OrderItem>> mappedCustomerOrders = new HashMap<>();
      mappedCustomerOrders.put("orders", orderItems);

      return new ResponseEntity<>(mappedCustomerOrders, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
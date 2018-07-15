package com.abconline.controllers.basket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abconline.models.basket.Basket;
import com.abconline.models.order.OrderItem;
import com.abconline.repositories.basket.BasketRepository;
import com.abconline.repositories.customer.CustomerRepository;
import com.abconline.repositories.order.OrderItemRepository;
import com.abconline.utils.AbcOnlineStrings;

import static com.abconline.utils.AbcOnlineStrings.RESPONSE_MESSAGE_KEY;
import static com.abconline.utils.AbcOnlineStrings.SUCCESS_KEY;

@RestController
@RequestMapping(value = "/baskets")
public class BasketController {

  @Autowired
  private final BasketRepository basketRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  public BasketController(BasketRepository basketRepository) {
    this.basketRepository = basketRepository;
  }

  @PostMapping(value = "/add/{customerId}")
  public ResponseEntity<?> create(@PathVariable("customerId") long customerId,
      @RequestBody OrderItem item) {

    if (!customerRepository.findById(customerId).isPresent()) {
      return new ResponseEntity<>(String.format("Unable to proceed due to unknown customerId value %1$s.", customerId), HttpStatus.UNAUTHORIZED);
    }

    if (Objects.nonNull(item)) {
      LocalDate now = LocalDate.now();

      item.setDateOfPurchase(now);
      OrderItem savedOrderItem = orderItemRepository.save(item);

      Basket savedBasket = basketRepository.save(new Basket(now, customerId, savedOrderItem.getItemId()));

      Map<String, String> responsePayload = new HashMap<>();
      responsePayload.put(AbcOnlineStrings.STATUS_KEY, SUCCESS_KEY);
      responsePayload.put(RESPONSE_MESSAGE_KEY, String.format("Successfully created basket %1$s for customer with id %2$s",
          savedBasket.getId(), customerId));

      return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
    }

    return new ResponseEntity<>("Unable to create Cart due to empty request payload",
        HttpStatus.PRECONDITION_FAILED);
  }

  @DeleteMapping(value = "/{basketId}/{customerId}")
  public ResponseEntity<?> removeItemFromBasket(@PathVariable("basketId") long basketId,
      @PathVariable("customerId") long customerId) {

    // confirm basketId and cus
    if (!basketRepository.existsById(basketId)) {
      return new ResponseEntity<>(String.format("Basket with id %1$s does not exist.", basketId),
          HttpStatus.NOT_FOUND);
    }

    if (!customerRepository.existsById(customerId)) {
      return new ResponseEntity<>(String.format("No customer with id %1$s exists.", customerId),
          HttpStatus.NOT_FOUND);
    }

    Basket basketInfo = basketRepository.getOne(basketId);

    if (!orderItemRepository.existsById(basketInfo.getOrderItemsItemId())) {
      return new ResponseEntity<>(String.format("No item(s) exists for basket %1$s.", basketId),
          HttpStatus.NOT_FOUND);

    } else {

      orderItemRepository.deleteById(basketInfo.getOrderItemsItemId());
      return new ResponseEntity<>(String.format("Successfully deleted item %1$s from basket %2$s",
          basketInfo.getOrderItemsItemId(), basketId), HttpStatus.OK);
    }
  }

  @GetMapping
  public ResponseEntity<List<Basket>> list() {
    if (basketRepository.findAll().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(new ArrayList<>(basketRepository.findAll()), HttpStatus.OK);
  }

  @PutMapping(value = "/{basketId}/add")
  public ResponseEntity<?> addToBasket(@PathVariable("basketId") long basketId, @RequestBody OrderItem item) {
    if (basketRepository.findById(basketId).isPresent()) {
      Basket existingBasket = basketRepository.getOne(basketId);
      existingBasket.setUpdatedAt(LocalDate.now());

      basketRepository.save(existingBasket);
      return new ResponseEntity<>(existingBasket, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping(value = "/{basketId}/add/{customerId}")
  public ResponseEntity<?> addToBasket(@PathVariable("basketId") long basketId,
      @PathVariable("customerId") long customerId,
      @RequestBody OrderItem item) {

    // brand new basket?
    if (basketRepository.findById(basketId).isPresent()) {

      // logged-in or valid Customer?
      if (customerRepository.findById(customerId).isPresent()) {

      }

      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteFromBasket(@PathVariable("id") long id, @RequestBody OrderItem item) {
    if (basketRepository.findById(id).isPresent()) {
      Basket existingBasket = basketRepository.getOne(id);

      existingBasket.setUpdatedAt(LocalDate.now());

      basketRepository.save(existingBasket);
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
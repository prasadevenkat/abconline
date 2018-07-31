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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abconline.daos.basket.BasketDao;
import com.abconline.daos.customer.CustomerDao;
import com.abconline.models.basket.Basket;
import com.abconline.models.order.OrderItem;
import com.abconline.utils.AbcOnlineStrings;

import static com.abconline.utils.AbcOnlineStrings.EMPTY_PAYLOAD_MESSAGE;
import static com.abconline.utils.AbcOnlineStrings.RESPONSE_MESSAGE_KEY;
import static com.abconline.utils.AbcOnlineStrings.SUCCESS_KEY;

@RestController
@RequestMapping(value = "/baskets")
public class BasketController {

  private final BasketDao basketDao;
  private final CustomerDao customerDao;

  @Autowired
  public BasketController(BasketDao basketDao, CustomerDao customerDao) {
    this.basketDao = basketDao;
    this.customerDao = customerDao;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Basket basket) {
    if (Objects.isNull(basket)) {
      return new ResponseEntity<>(EMPTY_PAYLOAD_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    if (CollectionUtils.isEmpty(basket.getOrderItems())) {
      return new ResponseEntity<>("Cannot create a basket without order items.", HttpStatus.BAD_REQUEST);
    }

    Basket savedBasket;

    if (Objects.nonNull(basket.getCustomerBasket())) {
      savedBasket = basketDao.save(new Basket(basket.getCreatedAt(), basket.getUpdatedAt(), basket.getOrderItems()));

    } else {
      savedBasket = basketDao.save(new Basket(basket.getCreatedAt(), basket.getUpdatedAt(), basket.getOrderItems(), basket.getCustomerBasket()));
    }

    Map<String, String> responsePayload = new HashMap<>();
    responsePayload.put(AbcOnlineStrings.STATUS_KEY, SUCCESS_KEY);
    responsePayload.put(RESPONSE_MESSAGE_KEY, String.format("Successfully created basket %1$s", savedBasket.getId()));

    return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{basketId}/customer/{customerId}")
  public ResponseEntity<?> removeItemFromBasket(@PathVariable("basketId") long basketId,
      @PathVariable("customerId") long customerId, @RequestBody OrderItem itemToRemove) {

    // confirm basketId and cus
    if (!basketDao.existsById(basketId)) {
      return new ResponseEntity<>(String.format("Basket with id %1$s does not exist.", basketId),
          HttpStatus.NOT_FOUND);
    }

    if (Objects.isNull(itemToRemove)) {
      return new ResponseEntity<>("Empty order item payload received, so cannot proceed.", HttpStatus.BAD_REQUEST);
    }

    Basket basketFound = basketDao.getOne(basketId);

    if (Objects.nonNull(basketFound.getCustomerBasket()) && !(basketFound.getCustomerBasket().getId() == customerId)) {
      return new ResponseEntity<>(String.format("Basket with id %1$s does not match customer with id %2$s.",
          basketId, customerId), HttpStatus.UNAUTHORIZED);
    }

    if (CollectionUtils.isEmpty(basketFound.getOrderItems())) {
      return new ResponseEntity<>(String.format("No item(s) exists for basket %1$s.", basketId), HttpStatus.NOT_FOUND);

    } else {

      if (basketFound.getOrderItems().remove(itemToRemove)) {
        Basket basketContent = basketDao.save(basketFound);

        return new ResponseEntity<>(String.format("Successfully deleted item %1$s from basket %2$s",
            itemToRemove.getItemId(), basketContent.getId()), HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping
  public ResponseEntity<List<Basket>> list() {
    if (basketDao.findAll().isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(new ArrayList<>(basketDao.findAll()), HttpStatus.OK);
  }

  @PutMapping(value = "/{basketId}")
  public ResponseEntity<Basket> addToBasket(@PathVariable("basketId") long basketId, @RequestBody OrderItem item) {
    final Basket[] savedExisting = {null};

    basketDao.findById(basketId).ifPresent(existingBasket -> {
      existingBasket.setUpdatedAt(LocalDate.now());
      existingBasket.addOrderItem(item);
      savedExisting[0] = basketDao.save(existingBasket);
    });

    return new ResponseEntity<>(savedExisting[0], HttpStatus.OK);
  }

  @PutMapping(value = "/{basketId}/add/{customerId}")
  public ResponseEntity<?> addToBasket(@PathVariable("basketId") long basketId,
      @PathVariable("customerId") long customerId,
      @RequestBody OrderItem item) {

    // brand new basket?
    if (basketDao.findById(basketId).isPresent()) {

      // logged-in or valid Customer?
      if (customerDao.findById(customerId).isPresent()) {

      }

      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteFromBasket(@PathVariable("id") long id, @RequestBody OrderItem item) {
    if (basketDao.findById(id).isPresent()) {
      Basket existingBasket = basketDao.getOne(id);

      existingBasket.setUpdatedAt(LocalDate.now());

      basketDao.save(existingBasket);
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
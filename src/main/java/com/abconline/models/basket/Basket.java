package com.abconline.models.basket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import com.abconline.models.customer.Customer;
import com.abconline.models.order.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * models a shopping cart (called basket in abconline).
 * @author etimbukudofia
 */
@Entity
@Table(name = "baskets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Basket {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate createdAt;

  @Column(name = "updated_at")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate updatedAt;

  @Column(name = "order_items")
  @OneToMany(mappedBy = "basket")
  private List<OrderItem> orderItems;

  @JsonIgnore
  @OneToOne
  private Customer customerBasket;

  public Basket() {}

  public Basket(LocalDate createdAt, LocalDate updatedAt, List<OrderItem> orderItems) {
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.orderItems = orderItems;
  }

  public Basket(LocalDate createdAt, LocalDate updatedAt, List<OrderItem> orderItems, Customer customer) {
    this(createdAt, updatedAt, orderItems);
    this.customerBasket = customer;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public Customer getCustomerBasket() {
    return customerBasket;
  }

  public void addOrderItem(OrderItem item) {
    if (CollectionUtils.isEmpty(this.orderItems)) {
      this.orderItems = new ArrayList<>();
    }

    if (Objects.nonNull(item)) {
      this.orderItems.add(item);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Basket basket = (Basket) o;
    return Objects.equals(id, basket.id) &&
            Objects.equals(createdAt, basket.createdAt) &&
            Objects.equals(updatedAt, basket.updatedAt) &&
            Objects.equals(orderItems, basket.orderItems) &&
            Objects.equals(customerBasket, basket.customerBasket);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, orderItems, customerBasket);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("createdAt", createdAt)
            .append("updatedAt", updatedAt)
            .append("orderItems", orderItems)
            .append("customerBasket", customerBasket)
            .toString();
  }
}
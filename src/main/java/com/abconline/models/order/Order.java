package com.abconline.models.order;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * models an order placed on abconline platform
 * @author etimbukudofia
 */
@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "order_items_item_id")
  private Long orderItemsItemId;

  @Column(name = "customers_id")
  private Long customerId;

  @Column(name = "created_on")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate createdOn;

  @Column(name = "updated_on")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate updatedOn;

  public Order() {
  }

  public Order(Long orderItemsItemId, Long customerId, LocalDate createdOn) {
    this.orderItemsItemId = orderItemsItemId;
    this.customerId = customerId;
    this.createdOn = createdOn;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDate createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDate getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDate updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Long getOrderItemsItemId() {
    return orderItemsItemId;
  }

  public void setOrderItemsItemId(Long orderItemsItemId) {
    this.orderItemsItemId = orderItemsItemId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("orderItemsItemId", orderItemsItemId)
        .append("customerId", customerId)
        .append("createdOn", createdOn)
        .append("updatedOn", updatedOn)
        .toString();
  }
}
package com.abconline.models.basket;

import java.time.LocalDate;

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

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "order_items_item_id")
  private Long orderItemsItemId;

  public Basket() {}

  public Basket(LocalDate createdAt, Long customerId, Long orderItemsItemId) {
    this.createdAt = createdAt;
    this.customerId = customerId;
    this.orderItemsItemId = orderItemsItemId;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getOrderItemsItemId() {
    return orderItemsItemId;
  }

  public void setOrderItemsItemId(Long orderItemsItemId) {
    this.orderItemsItemId = orderItemsItemId;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .append("customerId", customerId)
        .append("orderItemsItemId", orderItemsItemId)
        .toString();
  }
}
package com.abconline.models.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.abconline.models.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @Column(name = "created_on")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate createdOn;

  @Column(name = "updated_on")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate updatedOn;

  @Column(name = "items")
  @OneToMany(mappedBy = "order")
  private List<OrderItem> items;

  @JsonIgnore
  @ManyToOne
  private Customer customer;

  public Order() {
  }

  public Order(LocalDate createdOn, LocalDate updatedOn, List<OrderItem> items, Customer customer) {
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
    this.items = items;
    this.customer = customer;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getCreatedOn() {
    return createdOn;
  }

  public LocalDate getUpdatedOn() {
    return updatedOn;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public Customer getCustomer() {
    return customer;
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
            .append("createdOn", createdOn)
            .append("updatedOn", updatedOn)
            .append("items", items)
            .append("customer", customer)
            .toString();
  }
}
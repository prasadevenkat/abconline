package com.abconline.models.customer;

import com.abconline.models.basket.Basket;
import com.abconline.models.order.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * models a customer that places an {@link com.abconline.models.order.Order} on absonline.
 * @author etimbukudofia
 */
@Entity
@Table(name = "customers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email_address")
  private String emailAddress;

  @Column(name = "date_of_birth")
  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate dateOfBirth;

  @Column(name = "orders")
  @OneToMany(mappedBy = "customer")
  private List<Order> orders;

  @JoinColumn(name = "basket")
  @OneToOne(mappedBy = "customerBasket")
  private Basket basket;

  public Customer() {}

  /**
   *
   * @param firstName     first name string
   * @param lastName      last name
   * @param emailAddress  customer email address
   * @param dateOfBirth   customers DoB
   */
  public Customer(String firstName, String lastName, String emailAddress,
      LocalDate dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.dateOfBirth = dateOfBirth;
  }

  public Customer(String firstName, String lastName, String emailAddress, LocalDate dateOfBirth, List<Order> orders,
                  Basket basket) {
    this(firstName, lastName, emailAddress, dateOfBirth);
    this.orders = orders;
    this.basket = basket;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public Basket getBasket() {
    return basket;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return Objects.equals(id, customer.id) &&
            Objects.equals(emailAddress, customer.emailAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, emailAddress);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .append("emailAddress", emailAddress)
            .append("dateOfBirth", dateOfBirth)
            .append("orders", orders)
            .toString();
  }
}
package com.abconline.models.customer;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return Objects.equals(id, customer.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("firstName", firstName)
        .append("lastName", lastName)
        .append("dateOfBirth", dateOfBirth)
        .append("emailAddress", emailAddress)
        .toString();
  }
}
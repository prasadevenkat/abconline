package com.abconline.models;

import java.time.LocalDate;

import org.junit.Test;

import com.abconline.models.customer.Customer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerTest {

  @Test
  public void shouldReturnToString() {
    // Given
    final Customer customer = new Customer("Oliver", "Tester", "test@tester.com", LocalDate.of(2011, 11, 7));
    customer.setId(90L);

    // When
    String toString = customer.toString();

    // Then
    assertThat(toString, is(
        "Customer[id=90,firstName=Oliver,lastName=Tester,emailAddress=test@tester.com,dateOfBirth=2011-11-07,orders=<null>]"));
  }
}
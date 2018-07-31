package com.abconline.models;

import com.abconline.models.customer.Customer;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomerTest {

  @Test
  public void shouldReturnToString() {
    // Given
    final Customer customer = new Customer("Oliver", "Tester", "test@tester.com", LocalDate.now().minusYears(40));
    customer.setId(90L);

    // When
    String toString = customer.toString();

    // Then
    assertThat(toString, is(
        "Customer[id=90,firstName=Oliver,lastName=Tester,emailAddress=test@tester.com,dateOfBirth=1978-07-31,orders=<null>]"));
  }
}
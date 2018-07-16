package com.abconline.controllers;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.abconline.controllers.customer.CustomerController;
import com.abconline.models.customer.Customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest extends BaseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldCreateValidCustomerAndReturnStatus() throws Exception {
    Customer customer = new Customer("Oliver", "Tester", "test@tester.com", LocalDate.now().minusYears(40));

    mockMvc.perform(post("/api/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(customer)))
        .andExpect(status().isCreated())
        .andExpect(content().string(""))
        .andDo(print());//for debug

  }
}
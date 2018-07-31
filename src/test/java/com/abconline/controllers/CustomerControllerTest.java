package com.abconline.controllers;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.abconline.AbconlineApplication;
import com.abconline.daos.customer.CustomerDao;
import com.abconline.models.customer.Customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = WebEnvironment.MOCK,
    classes = {AbconlineApplication.class}
)
public class CustomerControllerTest extends BaseControllerTest {

  private Customer customer;

  private MockMvc mockMvc;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();

    this.customerDao.deleteAllInBatch();

    this.customer = customerDao.save(new Customer("Etimbuk", "Udofia", "etim@emailaddress.com", LocalDate.now().minusYears(25)));
  }

  @Test
  public void shouldCreateValidCustomerAndReturnStatus() throws Exception {
    Customer customer = new Customer("Oliver", "Tester", "test@tester.com", LocalDate.now().minusYears(40));

    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(customer)))
        .andExpect(status().isCreated())
        .andExpect(content().string(""))
        .andDo(print());//for debug

  }
}
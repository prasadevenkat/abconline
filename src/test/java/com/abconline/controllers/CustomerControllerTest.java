package com.abconline.controllers;

import com.abconline.AbconlineApplication;
import com.abconline.daos.customer.CustomerDao;
import com.abconline.models.basket.Basket;
import com.abconline.models.customer.Customer;
import com.abconline.models.order.Order;
import com.abconline.models.order.OrderItem;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
  public void setup() {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();

    this.customerDao.deleteAllInBatch();

    this.customer = customerDao.save(new Customer("Etimbuk", "Udofia", "etim@emailaddress.com", LocalDate.now().minusYears(25)));
  }

  @Test
  public void shouldCreateValidCustomerAndReturn201Status() throws Exception {
    Customer customer = new Customer("Oliver", "Tester", "test@tester.com", LocalDate.now().minusYears(40));

    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(customer)))
        .andExpect(status().isCreated())
//        .andExpect(content().string(""))
        .andDo(print());//for debug

  }

  @Test
  public void shouldCreateValidCustomerWithOrdersAndReturn201Status() throws Exception {
    List<OrderItem> items = new ArrayList<>();
    items.add(new OrderItem(2, "The Science of Getting Rich", "science-of-getting-rich-1910-ed",
            "Its a book", "60 GBP", LocalDate.now().minusDays(2)));
    items.add(new OrderItem(2, "Thanks for the feedback", "thx-for-the-feedback-ed",
            "Its a book", "60 GBP", LocalDate.now().minusDays(3)));

    LocalDate createdOn = LocalDate.now();
    LocalDate updatedOn = LocalDate.now();

    Basket basket = new Basket(createdOn, updatedOn, items);

    Order order = new Order(createdOn, updatedOn, items);

    Customer customer = new Customer("Oliver", "Tester", "test@tester.com",
            LocalDate.now().minusYears(40), Collections.singletonList(order), basket);

    mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json(customer)))
            .andExpect(status().isCreated())
//            .andExpect(content().string(customer.toString()))
            .andDo(print());//for debug
  }

  @Test
  public void shouldFindAndReturnStatus200() throws Exception {
    mockMvc.perform(get(String.format("/customers/%1$s", customer.getId()))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
//            .andExpect(content().string(json(customer)))
            .andDo(print());//for debug
  }

  @Test
  public void shouldNotFindAnyCustomerAndReturnStatus404() throws Exception {
    mockMvc.perform(get("/customers/000")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print());
  }
}
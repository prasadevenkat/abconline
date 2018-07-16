package com.abconline.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * base test for controller with autowired converters.
 * @author etimbukudofia
 */
@RunWith(SpringRunner.class)
public class BaseControllerTest {

  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {
    this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
        .findAny().get();
    Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
  }

  @SuppressWarnings("unchecked")
  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }
}
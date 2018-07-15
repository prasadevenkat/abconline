package com.abconline.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
  }

  public ResourceNotFoundException(String id) {
    super(String.format("Could not find Customer with id %1$s", id));
  }
}
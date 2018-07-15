package com.abconline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping
  public ResponseEntity<?> home() {
    return new ResponseEntity<>("<h1>Welcome to ABC Online RESTful API</h1>.", HttpStatus.OK);
  }

}
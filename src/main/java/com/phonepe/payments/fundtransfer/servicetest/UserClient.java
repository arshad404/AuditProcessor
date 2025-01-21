package com.phonepe.payments.fundtransfer.servicetest;

import feign.Headers;
import feign.RequestLine;
import java.util.List;

// Define the Feign client interface
public interface UserClient {

  @RequestLine("POST /users")
  @Headers({"Content-Type: application/json"})
  UserChange createUser(User user);

  @RequestLine("GET /error")
  @Headers({"Content-Type: application/json"})
  User getError(User user);

  @RequestLine("GET /users")
  List<User> getUsers();
}


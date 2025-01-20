package com.phonepe.payments.fundtransfer.codec;

import feign.Headers;
import feign.RequestLine;
import java.util.List;

// Define the Feign client interface
public interface UserClient {

  @RequestLine("POST /users")
  @Headers({"Content-Type: application/json"})
  User createUser(User user);

  @RequestLine("POST /error")
  @Headers({"Content-Type: application/json"})
  User getError();

  @RequestLine("GET /users")
  List<User> getUsers();
}


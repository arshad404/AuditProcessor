package com.phonepe.payments.fundtransfer.service;

import com.phonepe.payments.fundtransfer.codec.AuditEncoder;
import com.phonepe.payments.fundtransfer.codec.AuditErrorDecoder;
import com.phonepe.payments.fundtransfer.compression.Compression;
import com.phonepe.payments.fundtransfer.compression.ZstdCompression;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;
import feign.Feign;
import java.util.List;

public class Test {

  public static void main(String[] args) {
    var encoder = new ServiceEncoder();
    var decoder = new ServiceDecoder();

    var userClient = Feign.builder()
        .encoder(encoder)
        .decoder(decoder)
        .errorDecoder(new AuditErrorDecoder())
        .target(UserClient.class, "http://localhost:3000");

    // Create a new user
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    User createdUser = userClient.createUser(newUser);
    System.out.println("Created User: " + createdUser.getName() + ", Age: " + createdUser.getAge());

    // Get all users
    List<User> users = userClient.getUsers();
    users.forEach(user -> System.out.println("User: " + user.getName() + ", Age: " + user.getAge()));

    userClient.getError();
//    users.forEach(user -> System.out.println("User: " + user.getName() + ", Age: " + user.getAge()));
  }
}
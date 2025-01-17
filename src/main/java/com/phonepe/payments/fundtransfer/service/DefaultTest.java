package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditErrorDecoder;
import com.phonepe.payments.fundtransfer.codec.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditDecoder;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditEncoder;
import com.phonepe.payments.fundtransfer.codec.DefaultContextStore;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.log4j.BasicConfigurator;

public class DefaultTest {
  public static void main(String[] args) throws AuditRequestContextException {

    BasicConfigurator.configure();

    DefaultContextStore defaultContextStore = new DefaultContextStore(new ObjectMapper());
    TestAuditContextStore testAuditContextStore = new TestAuditContextStore(defaultContextStore);

    var encoder = DefaultAuditEncoder.builder().auditContextStore(testAuditContextStore).build();
    var decoder = DefaultAuditDecoder.builder().auditContextStore(testAuditContextStore).build();

    var userClient = Feign.builder()
        .encoder(encoder)
        .decoder(decoder)
        .requestInterceptor(new TransactionIdInterceptor())
        .errorDecoder(new AuditErrorDecoder())
        .target(UserClient.class, "http://localhost:3000");

    // Create a new user
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    User createdUser = userClient.createUser(newUser);
    System.out.println("Created User: " + createdUser.getName() + ", Age: " + createdUser.getAge());
////
////    // Get all users
//    List<User> users = userClient.getUsers();
//    users.forEach(user -> System.out.println("User: " + user.getName() + ", Age: " + user.getAge()));

    userClient.getError();
//    users.forEach(user -> System.out.println("User: " + user.getName() + ", Age: " + user.getAge()));
  }
}

class TransactionIdInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    // Generate or retrieve the transaction ID (e.g., from a UUID, or context)
    String transactionId = "some-unique-id"; // You can use UUID or another approach
    template.header("X-TRANSACTION-ID", transactionId);
  }
}
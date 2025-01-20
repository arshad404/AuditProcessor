package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditErrorDecoder;
import com.phonepe.payments.fundtransfer.codec.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditDecoder;
import com.phonepe.payments.fundtransfer.codec.DefaultAuditEncoder;
import com.phonepe.payments.fundtransfer.codec.DefaultContextStore;
import feign.Feign;
import java.util.List;
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
        .errorDecoder(new AuditErrorDecoder())
        .target(UserClient.class, "http://localhost:3000");

    // TESTING POST REQUEST
    System.out.println("--------TESTING POST REQUEST---------");
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    User createdUser = userClient.createUser(newUser);
    System.out.println("Created User: " + createdUser.getName() + ", Age: " + createdUser.getAge());

    // // TESTING GET REQUEST
    System.out.println("--------TESTING GET REQUEST---------");
    List<User> users = userClient.getUsers();
    users.forEach(user -> System.out.print("User: " + user.getName() + ", Age: " + user.getAge() + "  //  "));
    System.out.println();

    // TESTING ERROR DECODER
    System.out.println("--------TESTING ERROR DECODER---------");
    userClient.getError();
  }
}
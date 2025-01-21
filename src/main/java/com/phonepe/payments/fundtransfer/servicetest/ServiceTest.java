package com.phonepe.payments.fundtransfer.servicetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditErrorDecoder;
import com.phonepe.payments.fundtransfer.codec.DefaultContextStore;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import com.phonepe.payments.fundtransfer.exceptions.DataStoreException;
import feign.Feign;
import org.apache.log4j.BasicConfigurator;

public class ServiceTest {
  public static void main(String[] args) throws AuditRequestContextException, DataStoreException {

    BasicConfigurator.configure();
    var serviceAuditDataStore = new ServiceAuditDataStore();
    ServiceTransformer serviceTransformer = new ServiceTransformer();

    DefaultContextStore defaultContextStore = new DefaultContextStore(new ObjectMapper());
    ServiceAuditContextStore serviceAuditContextStore = new ServiceAuditContextStore(defaultContextStore);
    var encoder = ServiceAuditEncoder.builder().auditContextStore(serviceAuditContextStore).transformer(serviceTransformer).build();
    var decoder = ServiceAuditDecoder.builder().auditContextStore(serviceAuditContextStore).dataStore(serviceAuditDataStore).transformer(serviceTransformer).build();

    var userClient = Feign.builder()
        .encoder(encoder)
        .decoder(decoder)
        .errorDecoder(new AuditErrorDecoder<>(serviceAuditContextStore, serviceAuditDataStore))
        .target(UserClient.class, "http://localhost:3000");

    // Create a new user
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    UserChange createdUser = userClient.createUser(newUser);
    System.out.println("Created User: " + createdUser.getName());
  }


}

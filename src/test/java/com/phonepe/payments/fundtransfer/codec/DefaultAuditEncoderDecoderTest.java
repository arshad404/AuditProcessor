package com.phonepe.payments.fundtransfer.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultAuditEncoderDecoderTest {

  private TestAuditContextStore testAuditContextStore;
  DefaultContextStore defaultContextStore;
  UserClient userClient;
  TestAuditDataStore testStore;

  @BeforeAll
  static void setup() throws IOException {
    Logger.getRootLogger()
        .addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
    // Start the mock server
    MockServer.start();
  }

  @AfterAll
  static void teardown() {
    // Stop the mock server
    MockServer.stop();
  }

  @SneakyThrows
  @BeforeEach
  void before() {
    defaultContextStore = new DefaultContextStore(new ObjectMapper());
    testAuditContextStore = new TestAuditContextStore(defaultContextStore);
    testStore = new TestAuditDataStore();
    userClient = Feign.builder()
        .encoder(TestAuditEncoder.builder().auditContextStore(testAuditContextStore).build())
        .decoder(TestAuditDecoder.builder().auditContextStore(testAuditContextStore).dataStore(testStore).build())
        .errorDecoder(new TestAuditErrorDecoder(testAuditContextStore, testStore))
        .target(UserClient.class, "http://localhost:3000");
  }

  @SneakyThrows
  @Test
  void testSuccess2xxPOST() {
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    User createdUser = userClient.createUser(newUser);
    assertNotNull(createdUser);
    assertEquals("John Doe", createdUser.getName());
    assertEquals(30, createdUser.getAge());
    assertNotNull(testAuditContextStore.getAuditContext());
    assertEquals(User.class.getName(),
        testAuditContextStore.getAuditContext().getTypeFromType().getTypeName());
    assertEquals(1,testStore.getAuditDataMap().size());
  }

  @SneakyThrows
  @Test
  void testSuccess2xxGet() {
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    userClient.createUser(newUser);
    List<User> users = userClient.getUsers();
    assertNotNull(users);
    assertEquals("John Doe", users.get(0).getName());
    assertEquals(30, users.get(0).getAge());
    assertEquals(1,testStore.getAuditDataMap().size());
  }

  @Test
  void testSuccess5xxException() {
    User newUser = new User();
    newUser.setName("John Doe");
    newUser.setAge(30);
    Exception exception = assertThrows(UndeclaredThrowableException.class, () -> {
      userClient.getError(newUser);
    });
    String message = exception.getCause().getMessage();
    assertEquals(500, getStatusCode(message));
    System.out.println(exception.getMessage());
  }

  private int getStatusCode(String input) {
    String[] parts = input.split(", ");
    int statusCode = -1;
    for (String part : parts) {
      if (part.startsWith("Status:")) {
        String statusCodeStr = part.split(" ")[1];
        statusCode = Integer.parseInt(statusCodeStr);
        return statusCode;
      }
    }
    return statusCode;
  }
}

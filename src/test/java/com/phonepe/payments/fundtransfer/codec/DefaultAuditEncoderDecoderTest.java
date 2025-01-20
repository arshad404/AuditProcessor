package com.phonepe.payments.fundtransfer.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import java.io.IOException;
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
  private static TestServerManager serverManager;

  @BeforeAll
  static void setup() {
    Logger.getRootLogger()
        .addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
    try {

      String currentDirectory = System.getProperty("user.dir");

      // Print the current working directory
      System.out.println("Current working directory: " + currentDirectory);

      String fullPath = currentDirectory + "/src/test/java/com/phonepe/payments/fundtransfer/codec/test-server";
      // Initialize with your server start and stop commands
      serverManager = new TestServerManager(fullPath);
      serverManager.startServer();
    } catch (Exception e) {
      throw new RuntimeException("Failed to start the test server", e);
    }
  }

  @AfterAll
  public static void tearDown() throws IOException, InterruptedException {
    serverManager.stopServer();
  }

  @SneakyThrows
  @BeforeEach
  void before() {
    defaultContextStore = new DefaultContextStore(new ObjectMapper());
    testAuditContextStore = new TestAuditContextStore(defaultContextStore);
    testStore = new TestAuditDataStore();

    userClient = Feign.builder()
        .encoder(DefaultAuditEncoder.builder().auditContextStore(testAuditContextStore).build())
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
  void testSuccess2xxCreate() {
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

}

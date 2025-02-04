package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.Getter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.MDC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@Getter
public abstract class BaseAuditProcessorTest extends AuditProcessorWireMockServerTest {

  protected TestAuditDataStore testAuditDataStore;
  protected ExternalServiceClient externalServiceClient;
  protected DefaultRequestContextManager defaultRequestContextManager = new DefaultRequestContextManager();
  protected DefaultAuditErrorDecoder defaultAuditErrorDecoder;

  BaseAuditProcessorTest() {
    BasicConfigurator.configure();
    CodecRegistry.configure(ExternalServiceClient.class);

    testAuditDataStore = new TestAuditDataStore();
    defaultAuditErrorDecoder = new DefaultAuditErrorDecoder(defaultRequestContextManager,
        testAuditDataStore);
  }

  @BeforeEach
  protected void setupClient() {
    try {
      externalServiceClient = Feign.builder()
          .requestInterceptor(new DefaultAuditRequestInterceptor(defaultRequestContextManager))
          .encoder(new DefaultAuditEncoder(defaultRequestContextManager, new JacksonEncoder(),
              new ObjectMapper()))
          .decoder(new DefaultAuditDecoder(defaultRequestContextManager, new JacksonDecoder(),
              testAuditDataStore, new ObjectMapper()))
          .logger(new DefaultAuditResponseLogger(defaultRequestContextManager,
              testAuditDataStore))
          .errorDecoder(defaultAuditErrorDecoder)
          .logLevel(Level.FULL)
          .target(ExternalServiceClient.class, "http://localhost:3000");
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialise the Feign client ", e);
    }
  }

  @AfterEach
  protected void tearDown() {
    testAuditDataStore.auditDataMap.clear();
    MDC.clear();
  }
}

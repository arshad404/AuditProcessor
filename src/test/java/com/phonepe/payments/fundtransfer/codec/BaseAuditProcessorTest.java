package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec_new.CustomFeignClient;
import com.phonepe.payments.fundtransfer.codec_new.DefaultAuditDecoder;
import com.phonepe.payments.fundtransfer.codec_new.DefaultAuditEncoder;
import com.phonepe.payments.fundtransfer.codec_new.DefaultAuditRequestInterceptor;
import com.phonepe.payments.fundtransfer.codec_new.DefaultAuditResponseLogger;
import com.phonepe.payments.fundtransfer.codec_new.DefaultRequestContextManager;
import com.phonepe.payments.fundtransfer.codec_new.NoopAuditDataStore;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.log4j.BasicConfigurator;

public class BaseAuditProcessorTest extends AuditProcessorWireMockServerTest {

  protected TestAuditContextStore testAuditContextStore;
  protected DefaultContextStore defaultContextStore;
  protected ExternalServiceClient externalServiceClient;
  protected TestAuditDataStore testAuditDataStore;
  protected AuditRequestInterceptor auditRequestInterceptor;
  DefaultRequestContextManager defaultRequestContextManager = new DefaultRequestContextManager();

  BaseAuditProcessorTest() {
    BasicConfigurator.configure();
    defaultContextStore = new DefaultContextStore(new ObjectMapper());
    testAuditContextStore = new TestAuditContextStore(defaultContextStore);
    testAuditDataStore = new TestAuditDataStore();
    auditRequestInterceptor = new TestAuditRequestInterceptor(testAuditContextStore);
    try {
      externalServiceClient = Feign.builder()
          .client(new CustomFeignClient(defaultRequestContextManager))
          .requestInterceptor(new DefaultAuditRequestInterceptor(defaultRequestContextManager))
          .encoder(new DefaultAuditEncoder(defaultRequestContextManager, new JacksonEncoder(),
              new ObjectMapper()))
          .decoder(new DefaultAuditDecoder(defaultRequestContextManager, new JacksonDecoder(),
              new NoopAuditDataStore(), new ObjectMapper()))
          .logger(new DefaultAuditResponseLogger(defaultRequestContextManager,
              new NoopAuditDataStore()))
          .addCapability()
          .logLevel(Logger.Level.FULL) // Ensure FULL logging level is enabled
//          .encoder(TestAuditEncoder.builder().auditContextStore(testAuditContextStore).build())
//          .decoder(TestAuditDecoder.builder().auditContextStore(testAuditContextStore)
//              .dataStore(testAuditDataStore).build())
//          .requestInterceptor(auditRequestInterceptor)
//          .errorDecoder(new TestAuditErrorDecoder(testAuditContextStore, testAuditDataStore))
          .target(ExternalServiceClient.class, "http://localhost:3000")
      ;
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialise the Feign client ", e);
    }
  }
}

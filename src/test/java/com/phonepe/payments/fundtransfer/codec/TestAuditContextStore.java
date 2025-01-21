package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.exceptions.AuditRequestContextException;
import feign.RequestTemplate;
import feign.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestAuditContextStore implements AuditContextStore<DefaultAuditContext> {


  private final DefaultContextStore defaultContextStore;
  ObjectMapper objectMapper = new ObjectMapper();

  // Constructor accepting an already created DefaultContextStore for better flexibility | Enabling DI
  public TestAuditContextStore(DefaultContextStore defaultContextStore) {
    this.defaultContextStore = defaultContextStore;
  }

  // Alternatively, constructor that creates a DefaultContextStore internally, using the ObjectMapper
  public TestAuditContextStore(ObjectMapper objectMapper) {
    this.defaultContextStore = new DefaultContextStore(objectMapper);
  }

  @Override
  public void setAuditContext(Object o, Type type, RequestTemplate requestTemplate)
      throws AuditRequestContextException {
    try {
      var auditContext = DefaultAuditContext.builder()
          .id("key123")
          .headers(requestTemplate.headers())
          .method(requestTemplate.method())
          .url(requestTemplate.url())
          .requestObject(o).build();
      auditContext.setTypeFromType(type);
      auditContext.setRequestDataFromByte(objectMapper.writeValueAsBytes(o));
      defaultContextStore.setContext(auditContext);
    } catch (Exception e) {
      throw new AuditRequestContextException("error while setting the default context", e);
    }
  }

  @Override
  public void setAuditContext(Object object, Response response, Type type) {
    try {
      var context = defaultContextStore.getContext();
      context.setResponseData(objectMapper.writeValueAsString(object));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String setAuditContext(String methodKey, Response response) {
    try {
      String responseBody = getResponseBody(response);
      String message = String.format("Method: %s, Status: %d, Body: %s", methodKey, response.status(), responseBody);
      var context = defaultContextStore.getContext();
      context.setResponseData(objectMapper.writeValueAsString(message));
      return message;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DefaultAuditContext getAuditContext() throws AuditRequestContextException {
    try {
      return defaultContextStore.getContext();
    } catch (Exception e) {
      throw new AuditRequestContextException("error while getting the default context", e);
    }
  }

  private String getResponseBody(Response response) throws IOException {
    if (response.body() == null) {
      return null;
    }
    // try-with-resources to ensure the scanner is closed automatically
    try (Scanner scanner = new Scanner(response.body().asInputStream(), StandardCharsets.UTF_8)) {
      // delimiter "\\A" to read the entire body as a single string
      return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
    }
  }
}
package com.phonepe.payments.fundtransfer.codec_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import java.lang.reflect.Type;

public class DefaultAuditContextStore implements AuditContextStore<DefaultAuditContext> {

  private final DefaultContextStore<DefaultAuditContext> defaultContextStore;

  // Constructor accepting an already created DefaultContextStore for better flexibility | Enabling DI
  public DefaultAuditContextStore(DefaultContextStore<DefaultAuditContext> defaultContextStore) {
    this.defaultContextStore = defaultContextStore;
  }

  // Alternatively, constructor that creates a DefaultContextStore internally, using the ObjectMapper
  public DefaultAuditContextStore(ObjectMapper objectMapper) {
    this.defaultContextStore = new DefaultContextStore<>(DefaultAuditContext.class, objectMapper);
  }

  @Override
  public void setAuditContext(Object o, Type type, RequestTemplate requestTemplate) {
    try {
      defaultContextStore.setContext(new DefaultAuditContext());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public DefaultAuditContext getAuditContext() {
    try {
      return defaultContextStore.getContext();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
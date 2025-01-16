package com.phonepe.payments.fundtransfer.codec_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import java.lang.reflect.Type;

public class DefaultAuditContextStore implements AuditContextStore {

  private final ContextStore contextStore;
  public DefaultAuditContextStore(ObjectMapper objectMapper) {
    this.contextStore = new ContextStore(objectMapper);
  }

  @Override
  public void setAuditContext(Object o, Type type, RequestTemplate requestTemplate) {
    try {
      contextStore.setContext(getAuditContextKey(), new ContextStoreDao(
          getAuditContextKey(), o, requestTemplate.body(), type, requestTemplate.method(), requestTemplate.url()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object getAuditContext(String key, Class valueType) {
    try {
      return contextStore.getContext(getAuditContextKey(), valueType);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public final String auditContextKey = "AUDIT_KEY";
  public String getAuditContextKey() {
    return auditContextKey;
  }
}

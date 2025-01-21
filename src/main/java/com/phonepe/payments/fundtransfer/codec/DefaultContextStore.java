package com.phonepe.payments.fundtransfer.codec;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.exceptions.ContextStoreException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class DefaultContextStore implements ContextStore<DefaultAuditContext> {

  public static final String AUDIT_CONTEXT_KEY = "AUDIT_CONTEXT_KEY";
  private final ObjectMapper objectMapper;

  public DefaultContextStore(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void setContext(DefaultAuditContext value) throws ContextStoreException {
    try {
      // Serialize the value to JSON string
      String jsonValue = objectMapper.writeValueAsString(value);
      MDC.put(value.getId(), jsonValue);
      MDC.put(AUDIT_CONTEXT_KEY, value.getId());
    } catch (Exception e) {
      log.error("Failed to serialize object: {}", e.getMessage());
      throw new ContextStoreException("Failed to serialize object: " + e.getMessage(), e);
    }
  }

  public DefaultAuditContext getContext() throws ContextStoreException {
    try {
      var contextKey = MDC.get(AUDIT_CONTEXT_KEY);
      if(isNull(contextKey)) {
        throw  new IllegalStateException("No context key is available");
      }
      var auditData = MDC.get(contextKey);
      if(isNull(auditData)) {
        throw  new IllegalStateException("No audit context is available");
      }
      return objectMapper.readValue(auditData, DefaultAuditContext.class);
    } catch (Exception e) {
      log.error("Failed to deserialize object: {}", e.getMessage());
      throw new ContextStoreException("Failed to deserialize object: " + e.getMessage(), e);
    }
  }
}
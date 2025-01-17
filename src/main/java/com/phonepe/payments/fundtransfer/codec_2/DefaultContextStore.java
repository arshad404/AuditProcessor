package com.phonepe.payments.fundtransfer.codec_2;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class DefaultContextStore<T extends AuditContext> implements IContextStore<T> {

  public static final String AUDIT_CONTEXT_KEY = "AUDIT_CONTEXT_KEY";
  private final ObjectMapper objectMapper;
  private final Class<T> classType;

  public DefaultContextStore(Class<T> classType, ObjectMapper objectMapper) {
    this.classType = classType;
    this.objectMapper = objectMapper;
  }

  public void setContext(T value) throws ContextStoreException {
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

  public T getContext() throws ContextStoreException {
    try {
      var contextKey = MDC.get(AUDIT_CONTEXT_KEY);
      if(isNull(contextKey)) {
        throw  new IllegalStateException("No audit context available");
      }
      var auditData = MDC.get(contextKey);
      if(isNull(auditData)) {
        throw  new IllegalStateException("No audit context available");
      }
      return objectMapper.readValue(auditData, classType);
    } catch (Exception e) {
      log.error("Failed to deserialize object: {}", e.getMessage());
      throw new ContextStoreException("Failed to deserialize object: " + e.getMessage(), e);
    }
  }
}
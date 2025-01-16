package com.phonepe.payments.fundtransfer.codec_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class ContextStore<T> implements IContextStore<T> {

  private final ObjectMapper objectMapper;
  public ContextStore(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void setContext(String key, T value) throws ContextStoreException {
    try {
      // Serialize the value to JSON string
      String jsonValue = objectMapper.writeValueAsString(value);
      MDC.put(key, jsonValue);
    } catch (Exception e) {
      log.error("Failed to serialize object: {}", e.getMessage());
      throw new ContextStoreException("Failed to serialize object: " + e.getMessage(), e);
    }
  }

  public T getContext(String key, Class<T> valueType) throws ContextStoreException {
    try {
      String jsonValue = MDC.get(key);
      if (jsonValue != null) {
        // Deserialize the JSON string back to an object
        return objectMapper.readValue(jsonValue, valueType);
      }
    } catch (Exception e) {
      log.error("Failed to deserialize object: {}", e.getMessage());
      throw new ContextStoreException("Failed to deserialize object: " + e.getMessage(), e);
    }
    return null;
  }
}
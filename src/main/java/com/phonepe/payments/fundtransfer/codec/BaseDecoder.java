package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.model.AuditRequestEntity;
import java.lang.reflect.Type;
import java.util.logging.Logger;

public abstract class BaseDecoder {

  private final Logger logger = Logger.getLogger("AuditLog");

  protected final ObjectMapper objectMapper;

  protected BaseDecoder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  protected void saveAuditData(Object response, Type type, AuditRequestEntity entity) {
    logger.info("Request: " +entity +" | Response: " +response +" | Type: " +type);
  }
}

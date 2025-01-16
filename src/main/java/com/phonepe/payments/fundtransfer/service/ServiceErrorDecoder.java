package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditErrorDecoder;
import com.phonepe.payments.fundtransfer.model.AuditRequestEntity;
import java.lang.reflect.Type;

public class ServiceErrorDecoder extends AuditErrorDecoder {

  protected ServiceErrorDecoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  protected void saveAuditData(Object response, Type type, AuditRequestEntity entity) {
    logger.info("Overrideden Request: " +entity +" | Response: " +response +" | Type: " +type);
  }
}

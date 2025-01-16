package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.model.AuditRequestEntity;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;
import org.slf4j.MDC;

public abstract class AuditEncoder implements Encoder {

  private final ObjectMapper objectMapper;

  protected AuditEncoder(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void encode(Object o, Type type, RequestTemplate requestTemplate) {
    try {
      setAuditContext(o, type, requestTemplate);
      transformRequest(o, type, requestTemplate);
    } catch (Exception e) {
      throw new EncodeException("Error encode audit data", e);
    }
  }

  private void setAuditContext(Object o, Type type, RequestTemplate requestTemplate) throws IOException {
    //Here set the audit data into MDC
    try {
      MDC.put("AUDIT_CONTEXT", objectMapper.writeValueAsString(getAuditRequestContext(o, type, requestTemplate)));
    } catch (JsonProcessingException e) {
      throw new EncodeException("Error encoding audit context", e);
    }
  }

  //Make this generic to allow implementation of custom audit context entities
  protected AuditRequestEntity getAuditRequestContext(Object o, Type type, RequestTemplate requestTemplate) throws IOException  {
    var requestData = objectMapper.writeValueAsBytes(o);
    return new AuditRequestEntity(UUID.randomUUID().toString(), requestData, type.getTypeName(),
        requestTemplate.methodMetadata().method().getName(), requestTemplate.url());
  }

  //This will be used to transform the request if required
  public void transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    //NOOP
  }
}


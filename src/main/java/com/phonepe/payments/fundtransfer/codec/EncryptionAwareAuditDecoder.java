package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public abstract class EncryptionAwareAuditDecoder extends AuditEncoder {

  protected EncryptionAwareAuditDecoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  //This will be used to transform the request if required
  public void transformRequest(Object o, Type type, RequestTemplate requestTemplate) {
    requestTemplate.body(encrypt(o, type, requestTemplate), StandardCharsets.UTF_8);
  }

  public abstract byte[] encrypt(Object o, Type type, RequestTemplate requestTemplate);

}

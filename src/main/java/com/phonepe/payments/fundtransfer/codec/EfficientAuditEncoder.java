package com.phonepe.payments.fundtransfer.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import java.lang.reflect.Type;

public abstract class EfficientAuditEncoder extends EncryptionAwareAuditDecoder {

  protected EfficientAuditEncoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public byte[] encrypt(Object o, Type type, RequestTemplate requestTemplate) {
    return new byte[0];
  }

  public abstract byte[] compress(Object o, Type type, RequestTemplate requestTemplate);

}

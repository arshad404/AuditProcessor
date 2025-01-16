package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditEncoder;
import feign.RequestTemplate;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class ServiceEncoder extends AuditEncoder {

  protected ServiceEncoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }


}

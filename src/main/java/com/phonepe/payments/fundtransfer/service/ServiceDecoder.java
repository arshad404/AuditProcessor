package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.AuditDecoder;
import com.phonepe.payments.fundtransfer.codec.DefaultCodec;
import feign.Response;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class ServiceDecoder extends AuditDecoder {

  protected ServiceDecoder(ObjectMapper objectMapper) {
    super(objectMapper);
  }

}

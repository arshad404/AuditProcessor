package com.phonepe.payments.fundtransfer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonepe.payments.fundtransfer.codec.Encoder;
import com.phonepe.payments.fundtransfer.compression.Compression;
import com.phonepe.payments.fundtransfer.database.AuditData;
import com.phonepe.payments.fundtransfer.model.AuditProcessorConfig;
import feign.RequestTemplate;
import feign.jackson.JacksonEncoder;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.MDC;

public class ServiceEncoder extends Encoder {

  ObjectMapper objectMapper = new ObjectMapper();
  JacksonEncoder jacksonEncoder = new JacksonEncoder();

  protected ServiceEncoder(Compression compression,
      AuditData auditData,
      AuditProcessorConfig auditProcessorConfig) {
    super(compression, auditData, auditProcessorConfig);
  }

  @Override
  protected byte[] extract(Object o, Type type, RequestTemplate requestTemplate) {
    // saving the transaction id to the SLF4j
    MDC.put("X-FT-TRANSACTION-ID", "test-id"); // will be fetched from the headers
    try {
      if(o instanceof User user) {
        System.out.println("Printing from the encoder: " + user.getName());
        return objectMapper.writeValueAsBytes(user);
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return new byte[0];
  }

  @Override
  protected void transform(Object o, Type type, RequestTemplate template) {
    try {
      // can use any encoder as all form the same Encode interface
      jacksonEncoder.encode(o, type, template);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
